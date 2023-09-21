package com.ssafy.rentserver.service;

import com.ssafy.rentserver.enums.SeatStatus;
import com.ssafy.rentserver.model.Seat;
import com.ssafy.rentserver.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    private final SeatRepository seatRepository;
//    private final StringRedisTemplate redisTemplate;

    public List<Seat> createGrid(int row, int col, int classNumber) {
        //TODO: 해당 반에 기본 좌석생성 및 좌석 상태를 저장 성공시 행,렬,반 리턴
        List<Seat> seats = new ArrayList<>();
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                var seat = Seat.builder()
                        .rowNum(i)
                        .colNum(j)
                        .price(new BigDecimal(1000))
                        .class_number(classNumber)
                        .SeatStatus(SeatStatus.POSSIBLE)
                        .build();
                seats.add(seat);
            }
        }
        log.info("{}", seats.toString());
        return seatRepository.saveAll(seats);
    }

    public void getAllSeat(int classNumber) {
        //TODO: 해당 반에 설정된 모든 좌석에 대한 데이터
    }

    public void getSeatInfo(int x, int y, int classNumber) {
        //TODO: 몇반인지랑 자리 위치를 받아 그 자리의 정보를 리턴
    }

    public void setSeatInfO(int x, int y, int classNumber) {
        //TODO: 몇반인지랑 자리 위치를 받아 해당 좌석의 정보 설정
    }

    public Optional<Seat> assignSeatToUser(String seatId, String userId) {
        //TODO: DB에 해당 좌석에 유저 할당. 유서 서비스에 그 유저에 할당된 좌석 전달

        return null;
    }

    public void requestSeat(String seatId, String userId) {
        //TODO: 해당 유저의 보유 포인트가 좌석 비용보다 많은지 검사하는 로직 추가
//        String key = "seat_" + seatId + "_queue";
//        redisTemplate.opsForList().leftPush(key, userId);
//        String firstUserId = redisTemplate.opsForList().rightPop(key);
//
//        if (Objects.equals(firstUserId, userId)) {
//            Optional<Seat> result = assignSeatToUser(seatId, userId);
//            if (result.isPresent()) {
//                System.out.println("좌석 성공");
//            } else {
//                System.out.println("좌석 실패");
//            }
//        }

    }

}
