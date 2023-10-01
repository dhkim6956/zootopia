package com.ssafy.memberserver.domain.students.controller;

import com.ssafy.memberserver.common.api.Api;
import com.ssafy.memberserver.common.api.ApiResponse;
import com.ssafy.memberserver.domain.students.dto.request.StudentDeleteRequest;
import com.ssafy.memberserver.domain.students.dto.request.StudentPointUpdateRequest;
import com.ssafy.memberserver.domain.students.dto.request.StudentUpdateRequest;
import com.ssafy.memberserver.domain.students.dto.response.StudentDeleteResponse;
import com.ssafy.memberserver.domain.students.dto.response.StudentInfoResponse;
import com.ssafy.memberserver.domain.students.dto.response.StudentPointUpdateResponse;
import com.ssafy.memberserver.domain.students.dto.response.StudentUpdateResponse;
import com.ssafy.memberserver.domain.students.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/member-server/api/student")
public class StudentController {
    private final StudentService studentService;

    @Operation(summary = "학생 조회")
    @GetMapping
    public Api<StudentInfoResponse> getStudent(@RequestParam String studentId){
        return Api.OK(studentService.getStudentInfo(studentId));
    }

    @Operation(summary = "학생 정보 수정")
    @PutMapping("/update")
    public Api<StudentUpdateResponse> studentUpdate(@RequestBody StudentUpdateRequest studentUpdateRequest){
        return Api.OK(studentService.studentUpdate(studentUpdateRequest));
    }

    @Operation(summary = "학생 탈퇴")
    @DeleteMapping("/delete")
    public Api<StudentDeleteResponse> studentDelete(@RequestBody StudentDeleteRequest studentDeleteRequest){
        return Api.OK(studentService.studentDelete(studentDeleteRequest));
    }
    @Operation(summary = "임대 학생 포인트 차감")
    @PutMapping("/student/point")
    public Api<StudentPointUpdateResponse> studentPointUpdate(@RequestBody StudentPointUpdateRequest studentPointUpdateRequest, String seatId){
        return Api.OK(studentService.studentPointUpdate(studentPointUpdateRequest, UUID.fromString(seatId)));
    }
}
