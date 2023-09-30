package com.ssafy.memberserver.domain.teachers.controller;

import com.ssafy.memberserver.common.api.ApiResponse;
import com.ssafy.memberserver.domain.teachers.dto.request.TeacherDeleteRequest;
import com.ssafy.memberserver.domain.teachers.dto.request.TeacherUpdateRequest;
import com.ssafy.memberserver.domain.teachers.service.TeacherService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RequiredArgsConstructor
@RestController
@RequestMapping("member-server/api/teacher")
public class TeacherController {
    private final TeacherService teacherService;

    @Operation(summary = "선생님 회원 수정")
    @PutMapping("/update")
    public ApiResponse teacherUpdate(@RequestBody TeacherUpdateRequest teacherUpdateRequest){
        return ApiResponse.success(teacherService.teacherUpdate(teacherUpdateRequest));
    }
    @Operation(summary = "선생님 회원 삭제")
    @DeleteMapping("/delete")
    public ApiResponse teacherDelete(@RequestBody TeacherDeleteRequest teacherDeleteRequest){
        return ApiResponse.success(teacherService.teacherDelete(teacherDeleteRequest));
    }
}
