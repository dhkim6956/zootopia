package com.ssafy.rentserver.repository;

import com.ssafy.rentserver.model.Seat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;
import java.util.List;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SeatCacheRepository {

    private final RedisTemplate<String, Object> seatRedisTemplate;
    private final static Duration SEAT_CACHE_TTL = Duration.ofDays(3);


    public void setSeat(Seat seat) {
        String key = getKey(seat.getId().toString());
        log.info("Set Seat to Redis {}:{}", key, seat);
        seatRedisTemplate.opsForValue().set(key, seat, SEAT_CACHE_TTL);
    }

    public Seat getSeat(String seatId) {
        String key = getKey(seatId);
        Seat seat = (Seat)seatRedisTemplate.opsForValue().get(key);
        log.info("Get data from Redis {}:{}", key, seat);
        return seat;
    }

    public void setSeats(List<Seat> seats){
        Seat seat = seats.get(0);
        String key = getListKey(seat.getSchool(), seat.getGrade(), seat.getClazzNumber());
        ListOperations<String, Object> listOperations = seatRedisTemplate.opsForList();
        listOperations.rightPush(key, seats.toArray());
        seatRedisTemplate.expire(key, SEAT_CACHE_TTL);
    }

    public List<Object> getSeats(String key) {
        ListOperations<String, Object> listOperations = seatRedisTemplate.opsForList();
        return listOperations.range(key, 0, -1);
    }


    private String getKey(String seatId) {
        return "SEAT:" + seatId;
    }

    public String getListKey(String school, int grade, int clazzNumber){
        return "seats:" + school + ":" + grade + ":" + clazzNumber;
    }
}
