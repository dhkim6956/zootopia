package com.ssafy.rentserver.service;

import com.ssafy.common.error.ErrorCode;
import com.ssafy.common.exception.ApiException;
import com.ssafy.rentserver.dto.SeatRequest;
import com.ssafy.rentserver.enums.SeatStatus;
import com.ssafy.rentserver.model.Seat;
import com.ssafy.rentserver.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    //TODO : 히스토리 테이블 생성해 기록 저장
    private static boolean isTransactionSuccess = false;
    private final SeatRepository seatRepository;
    private final StringRedisTemplate redisTemplate;

    public List<Seat> createGrid(int row, int col, int clazzNumber, int grade, String school) {
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                var seat = Seat.builder()
                        .rowNum(i)
                        .colNum(j)
                        .price(new BigDecimal(1000))
                        .clazzNumber(clazzNumber)
                        .grade(grade)
                        .school(school)
                        .SeatStatus(SeatStatus.AVAILABLE)
                        .build();
                seats.add(seat);
            }
        }
        log.info("{}", seats.toString());
        return seatRepository.saveAll(seats);
    }

    public Optional<List<Seat>> getAllSeat(int clazzNumber, int grade, String school, SeatStatus status) {
        return seatRepository.getAllSeats(clazzNumber, grade, school, status);
    }

    public Optional<Seat> getSeatInfo(UUID seatId) {
        return seatRepository.findById(seatId);
    }

    @Transactional
    public Seat setSeatInfo(UUID seatId, SeatRequest request) {
        Seat seat = seatRepository.findById(seatId).orElseThrow(()-> new ApiException(ErrorCode.BAD_REQUEST, "존재하지 않는 좌석입니다"));
        seat.changePrice(request.price());
        seat.changeStatus(request.seatStatus());
        return seat;
    }


    public Seat assignSeatToUser(UUID seatId, UUID userId) {
        //TODO: DB에 해당 좌석에 유저 할당. 유서 서비스에 그 유저에 할당된 좌석 전달
        //1. seat db에 해당 좌석에 유저 등록
        Seat seat = seatRepository.findById(seatId).orElseThrow(()-> new ApiException(ErrorCode.BAD_REQUEST, "존재하지 않는 좌석입니다"));
        seat.setUserId(userId);
        //2. rent-service-success에 이벤트 생성 -> seatId를 보낸다.
        return seatRepository.save(seat);
    }

    public void isSuccessUserTransaction(){
        //TODO: assign에서 rent-service-success에 이벤트를 퍼블리쉬 한 후
        // user-service에서 해당 유저의 포인트를 차감하고 좌석을 할당했는가에 대한 토픽 구독
        // 메시지에는 좌석의 id, 성공여부가 들어가있다.
        // 메시지에 들어있는 seatId와 전달받은 seatId를 비교해 결과 리턴
        // status가 실패면 해당 좌석의 userId null 로 다시 변경.
        Map<String, String> result = new HashMap<>();
        result.put("seatId", "134");
        result.put("status", "FAIL");
        UUID seatId = UUID.fromString(result.get("seatId"));
        String status = result.get("status");
        if (status.equals("FAIL")) {
            Seat seat = Optional.ofNullable(seatRepository.findById(seatId)).get().orElseThrow(
                    () -> new ApiException(ErrorCode.BAD_REQUEST)
            );
            seat.changeStatus(SeatStatus.AVAILABLE);
        }
    }

    public void requestSeat(UUID seatId, UUID userId) {
        //TODO: 해당 유저의 보유 포인트가 좌석 비용보다 많은지 검사하는 로직 추가
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "잘못된 좌석 요청입니다."));
        if (!seat.getSeatStatus().equals(SeatStatus.AVAILABLE)){
            throw new ApiException(ErrorCode.BAD_REQUEST,"이용할 수 없는 좌석입니다.");
        }
        String key = "{seat_" + seatId.toString() + "}_queue"; //해시태그를 사용해 같은 키 = 같은 노드 위치 하게 설정
        redisTemplate.opsForList().leftPush(key, userId.toString());
        //TODO: 첫 사용자 실패시 그 다음 우선순위 시도 로직 추가
        while (!isTransactionSuccess && redisTemplate.hasKey(key)){
            String firstUserId = redisTemplate.opsForList().rightPop(key);
            if (firstUserId == null) {
                throw new ApiException(ErrorCode.BAD_REQUEST, "잘못된 요청입니다");
            }
            if (firstUserId.equals(userId.toString())) {
                assignSeatToUser(seatId, userId);
                isSuccessUserTransaction();
            }
            if (seat.getSeatStatus().equals(SeatStatus.INUSE)){
                isTransactionSuccess = true;
            }
        }
        // 전부 실패
    }

}
