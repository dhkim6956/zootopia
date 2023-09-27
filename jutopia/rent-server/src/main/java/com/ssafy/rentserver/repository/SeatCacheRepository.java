package com.ssafy.rentserver.repository;

import com.ssafy.rentserver.model.Seat;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.time.Duration;

@Slf4j
@Repository
@RequiredArgsConstructor
public class SeatCacheRepository {

    private final RedisTemplate<String, Seat> seatRedisTemplate;
    private final static Duration SEAT_CACHE_TTL = Duration.ofDays(3);


    public void setSeat(Seat seat) {
        String key = getKey(seat.getId().toString());
        log.info("Set Seat to Redis {}:{}", key, seat);
        seatRedisTemplate.opsForValue().set(key, seat, SEAT_CACHE_TTL);
    }

    public Seat getSeat(String seatId) {
        String key = getKey(seatId);
        Seat seat = seatRedisTemplate.opsForValue().get(key);
        log.info("Get data from Redis {}:{}", key, seat);
        return seat;
    }

    private String getKey(String seatId) {
        return "SEAT:" + seatId;
    }
}
