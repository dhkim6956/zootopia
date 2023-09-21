package com.ssafy.rentserver.controller;

import com.ssafy.common.api.Api;
import com.ssafy.rentserver.enums.SeatStatus;
import com.ssafy.rentserver.model.Seat;
import com.ssafy.rentserver.repository.SeatRepository;
import com.ssafy.rentserver.service.SeatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seat-service")
@Slf4j
public class SeatController {

    private final SeatService seatService;
    private final SeatRepository seatRepository;

    @GetMapping("")
    public Api<List<Seat>> test(){
        List<Seat> seats = seatService.createGrid(2,3,1);
        return Api.OK(seats);
    }


}
