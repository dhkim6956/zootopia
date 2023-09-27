package com.ssafy.rentserver.controller;

import com.ssafy.rentserver.enums.SeatStatus;
import com.ssafy.rentserver.model.Seat;
import com.ssafy.rentserver.repository.SeatCacheRepository;
import com.ssafy.rentserver.repository.SeatRepository;
import com.ssafy.rentserver.service.Producer;
import com.ssafy.rentserver.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seat-service")
@Slf4j
public class SeatController {

    private final SeatService seatService;
    private final SeatCacheRepository seatCacheRepository;

    @GetMapping("")
    public Seat testRedis(){
        Seat seat = Seat.builder().id(UUID.randomUUID())
                .seatStatus(SeatStatus.AVAILABLE)
                .pk(1L)
                .clazzNumber(1)
                .colNum(1)
                .grade(1)
                .price(new BigDecimal(1))
                .rowNum(1)
                .userId(UUID.randomUUID())
                .school("dfas")
                .build();
        seatCacheRepository.setSeat(seat);
        return seatCacheRepository.getSeat(seat.getId().toString());
    }

    private final Producer producer;

    @PostMapping("/kafka")
    public void testKafka(@RequestParam String msg){
        producer.pub(msg);
    }

}
