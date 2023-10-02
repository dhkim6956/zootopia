package com.ssafy.memberserver.domain.students.sign.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.memberserver.common.api.Api;
import com.ssafy.memberserver.domain.students.sign.dto.signIn.StudentSignInRequest;
import com.ssafy.memberserver.domain.students.sign.dto.signIn.StudentSignInResponse;
import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpRequest;
import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpResponse;
import com.ssafy.memberserver.domain.students.sign.service.StudentSignService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/member-server/api/student")
public class StudentSignController {
    private final StudentSignService studentSignService;
    @Operation(summary = "학생 로그인")
    @PostMapping("/sign-in")
    public Api<StudentSignInResponse> StudentSignIn(@RequestBody StudentSignInRequest studentSignInRequest) throws JsonProcessingException {
        return Api.OK(studentSignService.studentSignIn(studentSignInRequest));
    }
    @Operation(summary = "학생 회원가입")
    @PostMapping("/sign-up")
    public Api<StudentSignUpResponse> StudentSignUp(@RequestBody StudentSignUpRequest studentSignUpRequest){
        return Api.OK(studentSignService.studentSignUp(studentSignUpRequest));
    }
    @Operation(summary = "학생 회원가입 아이디 중복 체크")
    @GetMapping("/sign-up/{studentId}/duplicated")
    public Api<?> checkStudentIdDuplicated(@PathVariable String studentId){
        return Api.OK(studentSignService.checkStudentIdDuplicated(studentId));
    }
}
