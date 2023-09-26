package com.ssafy.memberserver.domain.pointtransaction.controller;

import com.ssafy.memberserver.common.api.ApiResponse;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointDepositRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointUpdateRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointWithDrawRequest;
import com.ssafy.memberserver.domain.pointtransaction.service.PointTransactionService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
    public class PointTransactionController {
        private final PointTransactionService pointTransactionService;

        @Operation(summary = "포인트 조회")
        @GetMapping
        public ApiResponse getAllpoint(@RequestParam String studentId) {
            return ApiResponse.success(pointTransactionService.getPointAll(UUID.fromString(studentId)));
        }
        @Operation(summary = "포인트 입금")
        @PostMapping("/point/deposit")
        public ApiResponse deposit(@RequestBody PointDepositRequest pointDepositRequest, @RequestParam String studentId, @RequestParam BigDecimal deposit){
            return ApiResponse.success(pointTransactionService.pointDeposit(pointDepositRequest,studentId,deposit));
        }
        @Operation(summary = "포인트 출금")
        @PostMapping("/point/withdraw")
        public ApiResponse withDraw(@RequestBody PointWithDrawRequest pointWithDrawRequest,@RequestParam String studentId,@RequestParam BigDecimal withDraw){
            return ApiResponse.success(pointTransactionService.pointWithDraw(pointWithDrawRequest,studentId,withDraw));
        }
    }