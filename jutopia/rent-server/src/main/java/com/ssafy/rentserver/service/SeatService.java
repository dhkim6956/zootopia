package com.ssafy.rentserver.service;

import com.ssafy.common.api.Api;
import com.ssafy.common.error.ErrorCode;
import com.ssafy.common.error.RentErrorCode;
import com.ssafy.common.exception.ApiException;
import com.ssafy.rentserver.dto.SeatChangeRequest;
import com.ssafy.rentserver.dto.SeatRequest;
import com.ssafy.rentserver.dto.SeatResponse;
import com.ssafy.rentserver.enums.SeatStatus;
import com.ssafy.rentserver.model.Seat;
import com.ssafy.rentserver.repository.SeatCacheRepository;
import com.ssafy.rentserver.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SeatService {

    //TODO : 히스토리 테이블 생성해 기록 저장
    private static boolean isTransactionSuccess = false;
    private final SeatRepository seatRepository;
    private final SeatCacheRepository seatCacheRepository;
    private final RedisTemplate<String, String> redisTemplate;

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

    public Api<?> changeSeatInfo(SeatChangeRequest request){
        Optional<Seat> seat = seatRepository.findById(request.getSeatId());
        if (seat.isEmpty()) {
            return Api.ERROR(RentErrorCode.BAD_REQUEST, "존재하지 않는 좌석입니다.");
        }
        var data = seat.get();
        data.changePrice(request.getPrice());
        data.changeUserId(request.getUserId());
        data.changeStatus(request.getSeatStatus());

        return Api.OK(data);
    }


    public Api<?> getAllSeat(int clazzNumber, int grade, String school) {
        String key = seatCacheRepository.getListKey(school, grade, clazzNumber);
        var seats = seatCacheRepository.getSeats(key);
        if (seats == null) {
            Optional<List<Seat>> seatList = seatRepository.getAllSeats(clazzNumber, grade, school);
            if (seatList.get().isEmpty()) {
                return Api.ERROR(RentErrorCode.BAD_REQUEST, "존재하지 않는 좌석 정보입니다.");
            }
            List<SeatResponse> response = seatList.get().stream().map(SeatResponse::toResponse).toList();
            seatCacheRepository.setSeats(response);
            return Api.OK(response);
        }

        return Api.OK(seats);
    }

    public Api<?> getSeatInfo(UUID seatId) {
        var seat = seatCacheRepository.getSeat(seatId.toString());
        if (seat == null) {
            Optional<Seat> getFromDb = seatRepository.findById(seatId);
            if (getFromDb.isEmpty()) {
                return Api.ERROR(RentErrorCode.BAD_REQUEST, "존재하지 않는 좌석입니다.");
            }
            seatCacheRepository.setSeat(getFromDb.get());
            return Api.OK(SeatResponse.toResponse(getFromDb.get()));
        }
        return Api.OK(SeatResponse.toResponse(seat));
    }


    public Api<?> requestSeat(String seatId, String userId) {
        //TODO: 기존 사용자와 동일한 사용자가 같은 자리를 신청한 것이라면 포인트를 1.5배 내도록 처리
        ValueOperations<String, String> ops = redisTemplate.opsForValue();

        Boolean lockAcquired = ops.setIfAbsent("lock:"+seatId,"locked",10, TimeUnit.SECONDS);
        if(Boolean.FALSE.equals(lockAcquired)) {
            return Api.ERROR(RentErrorCode.FAIL, "다른 사람이 먼저 신청헀습니다.");
        }

        try {
            var seat = seatCacheRepository.getSeat(seatId);
            if (seat.getSeatStatus() != SeatStatus.AVAILABLE) {
                return Api.ERROR(ErrorCode.BAD_REQUEST, "신청할 수 없는 좌석입니다.");
            }
            //TODO: 포인트 차감 로직 추가 feign을 통해 user-server에 요청
            var errorCode = 200;

            if (errorCode == 1002) {
                return Api.ERROR(RentErrorCode.POINT_LACK, "포인트가 부족합니다.");
            }

            seat.changeStatus(SeatStatus.INUSE);
            seat.changeUserId(UUID.fromString(userId));
            var newSeat = SeatResponse.toResponse(seatRepository.save(seat));

            String key = seatCacheRepository.getListKey(seat.getSchool(), seat.getGrade(), seat.getClazzNumber());
            seatCacheRepository.clearSeat(seatId);
            seatCacheRepository.clearSeats(key);

            return Api.OK(newSeat);
        } catch (Exception e){
            log.info("{}",e.toString());
            return Api.ERROR(RentErrorCode.SERVER_ERROR);
        }
        finally {
            redisTemplate.delete("lock:" + seatId);
        }
    }
}
