package com.ssafy.rentserver.controller;

import com.ssafy.rentserver.repository.SeatRepository;
import com.ssafy.rentserver.service.SeatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/seat-service/api/seat")
@Slf4j
public class SeatController {

    private final SeatService seatService;
    private final SeatRepository seatRepository;

    @GetMapping("/health_check")
    public String status() {
        return "It's working in Order Service on PORT";
    }
//    @GetMapping("")
//    public void test(){
//        seatService.redisTest();
//    }

}
