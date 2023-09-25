package com.ssafy.memberserver.domain.account.controller;

import com.ssafy.common.api.Api;
import com.ssafy.memberserver.common.api.ApiResponse;
import com.ssafy.memberserver.domain.account.dto.request.AccountDeleteRequest;
import com.ssafy.memberserver.domain.account.dto.response.AccountInfoResponse;
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

    @Operation(summary = "회원 계좌 조회")
    @GetMapping
    public ApiResponse getAccountInfo(String id, String studentId){
        return ApiResponse.success(accountService.getAccountInfo(UUID.fromString(id),studentId));
    }


    @Operation(summary = "회원 계좌 탈퇴")
    @DeleteMapping
    public ApiResponse deleteAccount(AccountDeleteRequest accountDeleteRequest, String id){
        return ApiResponse.success(accountService.deleteAccount(accountDeleteRequest, UUID.fromString(id)));
    }
}
