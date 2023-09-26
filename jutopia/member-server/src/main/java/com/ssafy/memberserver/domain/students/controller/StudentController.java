package com.ssafy.memberserver.domain.students.controller;

import com.ssafy.memberserver.common.api.ApiResponse;
import com.ssafy.memberserver.domain.students.dto.request.StudentDeleteRequest;
import com.ssafy.memberserver.domain.students.dto.request.StudentUpdateRequest;
import com.ssafy.memberserver.domain.students.service.StudentService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/student")
public class StudentController {
    private final StudentService studentService;

    @Operation(summary = "학생 조회")
    @GetMapping("")
    public ApiResponse getStudent(String id){
        return ApiResponse.success(studentService.getStudentInfo(UUID.fromString(id)));
    }

    @Operation(summary = "학생 정보 수정")
    @PutMapping
    public ApiResponse studentUpdate(@RequestBody StudentUpdateRequest studentUpdateRequest, String id){
        return ApiResponse.success(studentService.studentUpdate(studentUpdateRequest,UUID.fromString(id)));
    }

    @Operation(summary = "학생 탈퇴")
    @DeleteMapping
    public ApiResponse studentDelete(@RequestBody StudentDeleteRequest studentDeleteRequest, String id){
        return ApiResponse.success(studentService.studentDelete(studentDeleteRequest,UUID.fromString(id)));
    }
}
