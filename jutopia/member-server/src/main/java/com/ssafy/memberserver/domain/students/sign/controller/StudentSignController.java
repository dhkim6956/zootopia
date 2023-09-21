package com.ssafy.memberserver.domain.students.sign.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.memberserver.common.api.ApiResponse;
import com.ssafy.memberserver.domain.students.sign.dto.signIn.StudentSignInRequest;
import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpRequest;
import com.ssafy.memberserver.domain.students.sign.service.StudentSignService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/student")
public class StudentSignController {
    private final StudentSignService studentSignService;
    @Operation(summary = "학생 로그인")
    @PostMapping("/sign-in")
    public ApiResponse StudentSignIn(@RequestBody StudentSignInRequest studentSignInRequest) throws JsonProcessingException {
        return ApiResponse.success(studentSignService.studentSignIn(studentSignInRequest));
    }
    @Operation(summary = "학생 회원가입")
    @PostMapping("/sign-up")
    public ApiResponse StudentSignUp(@RequestBody StudentSignUpRequest studentSignUpRequest){
        return ApiResponse.success(studentSignService.studentSignUp(studentSignUpRequest));
    }
    @Operation(summary = "학생 회원가입 아이디 중복 체크")
    @GetMapping("/sign-up/{studentId}/duplicated")
    public ApiResponse checkStudentIdDuplicated(@PathVariable String studentId){
        return ApiResponse.success(studentSignService.checkStudentIdDuplicated(studentId));
    }
}
