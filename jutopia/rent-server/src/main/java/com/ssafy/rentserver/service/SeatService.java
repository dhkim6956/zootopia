package com.ssafy.rentserver.service;

import com.ssafy.common.error.ErrorCode;
import com.ssafy.common.exception.ApiException;
import com.ssafy.rentserver.dto.SeatRequest;
import com.ssafy.rentserver.dto.SeatResponse;
import com.ssafy.rentserver.enums.SeatStatus;
import com.ssafy.rentserver.model.Seat;
import com.ssafy.rentserver.repository.SeatCacheRepository;
import com.ssafy.rentserver.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    //TODO : 히스토리 테이블 생성해 기록 저장
    private static boolean isTransactionSuccess = false;
    private final SeatRepository seatRepository;
    private final SeatCacheRepository seatCacheRepository;

    public List<Seat> createGrid(int totalCount, int clazzNumber, int grade, String school) {
        //TODO : 해당 반의 좌석 정보가 변경됐을떄는 기존의 자리는?
        //만약 해당반, 학년, 학교 조합의 데이터가 존재한다 -> 전부 DELETED처리 후 새로 추가
        List<Seat> seats = new ArrayList<>();
        int count = 1;
        for (int i = 0; i < totalCount; i++) {
            var seat = Seat.builder()
                    .price(new BigDecimal(1000))
                    .clazzNumber(clazzNumber)
                    .grade(grade)
                    .school(school)
                    .position(count)
                    .seatStatus(SeatStatus.AVAILABLE)
                    .build();
            seats.add(seat);
            count++;
        }
        return seatRepository.saveAll(seats);
    }


    public List<SeatResponse> getAllSeat(int clazzNumber, int grade, String school) {
        String key = seatCacheRepository.getListKey(school, grade, clazzNumber);
        var seats = seatCacheRepository.getSeats(key);
        if (seats == null) {
            Optional<List<Seat>> seatList = seatRepository.getAllSeats(clazzNumber, grade, school);
            if (seatList.get().isEmpty()) {
                throw new ApiException(ErrorCode.BAD_REQUEST, "해당 데이터에 좌석들이 존재하지 않습니다.");
            }
            List<SeatResponse> response = seatList.get().stream().map(SeatResponse::toResponse).toList();
            seatCacheRepository.setSeats(response);
            return response;
        }

        return seats;
    }

    public Seat getSeatInfo(UUID seatId) {
        var seat = seatCacheRepository.getSeat(seatId.toString());
        if (seat == null) {
            seat = seatRepository.findById(seatId).orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "존재하지 않는 좌석입니다"));
            seatCacheRepository.setSeat(seat);
            return seat;
        }
        return seat;
    }

    @Transactional
    public Seat setSeatInfo(UUID seatId, SeatRequest request) {
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "존재하지 않는 좌석입니다"));
        seatCacheRepository.setSeat(seat);
        seat.changePrice(request.price());
        seat.changeStatus(request.seatStatus());
        return seat;
    }


    public Seat assignSeatToUser(UUID seatId, UUID userId) {
        //TODO: DB에 해당 좌석에 유저 할당. 유서 서비스에 그 유저에 할당된 좌석 전달
        //1. seat db에 해당 좌석에 유저 등록
        Seat seat = seatRepository.findById(seatId).orElseThrow(() -> new ApiException(ErrorCode.BAD_REQUEST, "존재하지 않는 좌석입니다"));
        seat.setUserId(userId);
        //2. rent-service-success에 이벤트 생성 -> seatId를 보낸다.
        return seatRepository.save(seat);
    }

    public void isSuccessUserTransaction() {
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

    }

}
