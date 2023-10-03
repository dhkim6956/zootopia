package com.ssafy.memberserver.domain.students.sign.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.error.ErrorCode;
import com.ssafy.memberserver.common.exception.ApiException;
import com.ssafy.memberserver.domain.students.entity.Student;
import com.ssafy.memberserver.domain.students.repository.StudentRepository;

import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpRequest;
import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpResponse;

import com.ssafy.memberserver.domain.teachers.repository.TeacherRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentSignService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public StudentSignUpResponse studentSignUp(StudentSignUpRequest studentSignUpRequest) {
        if (studentRepository.findByStudentId(studentSignUpRequest.getStudentId()).isPresent()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "아이디가 중복입니다.");
        }
        Student student = studentRepository.save(Student.from(studentSignUpRequest, passwordEncoder));
        studentRepository.flush();
        return StudentSignUpResponse.from(student);
    }
    public boolean checkIdDuplicated(String memberId) {
        boolean student = studentRepository.existsByStudentId(memberId);
        boolean teacher = teacherRepository.existsByTeacherId(memberId);
        if (student == false && teacher == false) {
            return false;
        }
        return true;
    }
}
