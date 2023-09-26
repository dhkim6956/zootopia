package com.ssafy.memberserver.domain.account.controller;

import com.ssafy.memberserver.common.api.ApiResponse;
import com.ssafy.memberserver.domain.account.dto.request.AccountDeleteRequest;
import com.ssafy.memberserver.domain.account.dto.request.CreateAccountRequest;
import com.ssafy.memberserver.domain.account.service.AccountService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/account")
public class AccountController {
    private final AccountService accountService;

    @Operation(summary = "학생 계좌 생성")
    @PostMapping
    public ApiResponse makeAccount(@RequestBody CreateAccountRequest createAccountRequest,@RequestParam String studentId){
        return ApiResponse.success(accountService.createAccount(createAccountRequest,studentId));
    }

    @Operation(summary = "학생 계좌 조회")
    @GetMapping
    public ApiResponse getAccountInfo(@RequestParam String id, @RequestParam String studentId){
        return ApiResponse.success(accountService.getAccountInfo(UUID.fromString(id),studentId));
    }


    @Operation(summary = "학생 계좌 탈퇴")
    @DeleteMapping
    public ApiResponse deleteAccount(AccountDeleteRequest accountDeleteRequest, String id){
        return ApiResponse.success(accountService.deleteAccount(accountDeleteRequest, UUID.fromString(id)));
    }
}
