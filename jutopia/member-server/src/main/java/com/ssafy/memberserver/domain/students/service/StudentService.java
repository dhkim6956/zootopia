package com.ssafy.memberserver.domain.students.service;

import com.ssafy.memberserver.common.api.Api;
import com.ssafy.memberserver.common.error.ErrorCode;
import com.ssafy.memberserver.common.exception.ApiException;
import com.ssafy.memberserver.domain.students.dto.request.StudentDeleteRequest;
import com.ssafy.memberserver.domain.students.dto.request.StudentPointUpdateRequest;
import com.ssafy.memberserver.domain.students.dto.request.StudentUpdateRequest;
import com.ssafy.memberserver.domain.students.dto.response.StudentDeleteResponse;
import com.ssafy.memberserver.domain.students.dto.response.StudentInfoResponse;
import com.ssafy.memberserver.domain.students.dto.response.StudentPointUpdateResponse;
import com.ssafy.memberserver.domain.students.dto.response.StudentUpdateResponse;
import com.ssafy.memberserver.domain.students.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;


@RequiredArgsConstructor
@Service
@Slf4j
public class StudentService {
    private final StudentRepository studentRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    public StudentInfoResponse getStudentInfo(String studentId){
            return studentRepository.findByStudentId(studentId)
                    .map(StudentInfoResponse::from)
                    .orElseThrow(() -> new ApiException(ErrorCode.STUDENT_NOT_FOUND,"존재하지 않는 학생입니다."));
        }
    @Transactional
    public StudentUpdateResponse studentUpdate(StudentUpdateRequest studentUpdateRequest){
        return studentRepository.findByStudentId(studentUpdateRequest.getStudentId())
                .filter(student -> passwordEncoder.matches(studentUpdateRequest.getStudentPwd(),student.getStudentPwd()))
                .map(student -> {
                    student.update(studentUpdateRequest,passwordEncoder);
                    return StudentUpdateResponse.of(true);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.STUDENT_NOT_FOUND,"존재하지 않는 학생입니다."));
    }
    @Transactional
    public StudentDeleteResponse studentDelete(StudentDeleteRequest studentDeleteRequest){
        return studentRepository.findByStudentId(studentDeleteRequest.getStudentId())
                .filter(student -> passwordEncoder.matches(studentDeleteRequest.getStudentPwd(),student.getStudentPwd()))
                .map(student -> {
                    student.delete(studentDeleteRequest);
                    return StudentDeleteResponse.of(true);
                })
                .orElseThrow(() -> new ApiException(ErrorCode.STUDENT_INVALID_INPUT,"아이디 또는 비밀번호가 틀렸습니다."));
    }
    @Transactional
    public Api<?> studentPointUpdate(StudentPointUpdateRequest studentPointUpdateRequest){
        var student = studentRepository.findByStudentId(studentPointUpdateRequest.getStudentId());
        if (student.isEmpty()) {
            return Api.ERROR(ErrorCode.STUDENT_NOT_FOUND, "존재하지 않는 학생입니다.");
        }
        try {
            var studentInfo = student.get();
            studentInfo.pointUpdate(studentPointUpdateRequest);
            studentInfo.changeSeat(studentPointUpdateRequest.getSeatId());
            return Api.OK(StudentPointUpdateResponse.of(true));
        } catch (ApiException e){
            return Api.ERROR(ErrorCode.STUDENT_POINT_ERROR, "포인트가 부족합니다.");
        }

    }
}
