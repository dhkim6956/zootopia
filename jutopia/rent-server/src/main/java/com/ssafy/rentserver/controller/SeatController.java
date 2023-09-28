package com.ssafy.rentserver.controller;

import com.ssafy.common.api.Api;
import com.ssafy.rentserver.dto.CreateRequest;
import com.ssafy.rentserver.dto.SeatResponse;
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
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class SeatController {

    private final SeatService seatService;

    @PostMapping("/seats")
    public Api<List<Seat>> creatSeate(@RequestBody CreateRequest request){
        log.info(request.toString());
        List<Seat> seats = seatService.createGrid(request.getTotalCount(), request.getClazzNumber(), request.getGrade(), request.getSchool());
        return Api.OK(seats);
    }

    @GetMapping("/seats")
    public Api<List<SeatResponse>> getAllSeats(@RequestParam String school,
                                              @RequestParam int grade,
                                              @RequestParam int clazzNumber)
    {
        var seats = seatService.getAllSeat(clazzNumber, grade, school);
        log.info("getAllSeats Response: {}", Api.OK(seats));
        return Api.OK(seats);
    }
}
