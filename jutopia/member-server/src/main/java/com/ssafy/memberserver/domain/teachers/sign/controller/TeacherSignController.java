package com.ssafy.memberserver.domain.teachers.sign.controller;

import com.ssafy.memberserver.common.api.ApiResponse;

import com.ssafy.memberserver.domain.mail.service.MailService;
import com.ssafy.memberserver.domain.teachers.sign.dto.SignIn.request.TeacherSignInRequest;
import com.ssafy.memberserver.domain.teachers.sign.dto.SignUp.request.TeacherSignUpRequest;
import com.ssafy.memberserver.domain.teachers.sign.service.TeacherSignService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member-server/api/teacher")
public class TeacherSignController {
    private final TeacherSignService teacherSignService;
    private final MailService mailService;

    @Operation(summary = "선생님 회원 가입")
    @PostMapping("/sign-up")
    public ApiResponse teacherSignUp(@RequestBody TeacherSignUpRequest teacherSignUpRequest){
        return ApiResponse.success(teacherSignService.teacherSignUp(teacherSignUpRequest));
    }
    @Operation(summary = "선생님 로그인")
    @PostMapping("/sign-in")
    public ApiResponse teacherSignIn(@RequestBody TeacherSignInRequest teacherSignInRequest){
        return ApiResponse.success(teacherSignService.teacherSignIn(teacherSignInRequest));
    }
    @Operation(summary = "선생님 아이디 중복 검사")
    @GetMapping("/sign-up/{teacherId}/duplicated")
    public ApiResponse checkTeacherIdDuplicated(@PathVariable String teacherId){
        return ApiResponse.success(teacherSignService.checkTeacherIdDuplicated(teacherId));
    }

    @Operation(summary = "이메일 인증")
    @PostMapping("/sign-in/mailConfirm")
    @ResponseBody
    public ApiResponse mailConfirm(@RequestParam("email") String email) throws Exception{
        return ApiResponse.success(mailService.sendSimpleMessage(email));
    }
}
