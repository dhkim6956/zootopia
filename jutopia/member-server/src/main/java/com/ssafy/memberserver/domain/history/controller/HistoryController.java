package com.ssafy.memberserver.domain.history.controller;

import com.ssafy.memberserver.common.api.ApiResponse;
import com.ssafy.memberserver.domain.history.dto.request.CreateInputRequest;
import com.ssafy.memberserver.domain.history.service.HistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping
public class HistoryController {
    private final HistoryService historyService;

    @Operation(summary = "계좌 전체 조회")
    @GetMapping
    public ApiResponse getAllhistory(@RequestParam String accountId){
        return ApiResponse.success(historyService.getAllHistoryByAccountId(UUID.fromString(accountId)));
    }
    @Operation(summary = "계좌에 값 넣기")
    @PostMapping
    public ApiResponse createInput(@RequestBody CreateInputRequest createInputRequest, String accountId){
        return ApiResponse.success(historyService.createInput(createInputRequest, UUID.fromString(accountId)));
    }
}
