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



}
