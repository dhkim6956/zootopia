package com.ssafy.memberserver.domain.students.service;

import com.ssafy.memberserver.common.enums.MemberStatus;
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
import java.util.Optional;
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
                .orElseThrow(() -> new NoSuchElementException("존재하지 않는 회원입니다."));
    }
    @Transactional
    public StudentUpdateResponse studentUpdate(StudentUpdateRequest studentUpdateRequest){
        return studentRepository.findByStudentId(studentUpdateRequest.studentId())
                .filter(student -> passwordEncoder.matches(studentUpdateRequest.studentPwd(),student.getStudentPwd()))
                .map(student -> {
                    student.update(studentUpdateRequest,passwordEncoder);
                    return StudentUpdateResponse.of(true);
                })
                .orElseThrow(() -> new NoSuchElementException("비밀번호가 일치하지 않습니다."));
    }
    @Transactional
    public StudentDeleteResponse studentDelete(StudentDeleteRequest studentDeleteRequest){
        return studentRepository.findByStudentId(studentDeleteRequest.studentId())
                .filter(student -> passwordEncoder.matches(studentDeleteRequest.studentPwd(),student.getStudentPwd()))
                .map(student -> {
                    student.delete(studentDeleteRequest);
                    return StudentDeleteResponse.of(true);
                })
                .orElseThrow(() ->  new NoSuchElementException("비밀번호가 일치하지 않습니다."));
    }
    @Transactional
    public StudentPointUpdateResponse studentPointUpdate(StudentPointUpdateRequest studentPointUpdateRequest,UUID seatId){
        return studentRepository.findByStudentId(studentPointUpdateRequest.studentId())
                .map(it ->{
                    log.info("{}","ewffwefwewefefewf");
                    it.pointUpdate(studentPointUpdateRequest);
                    return StudentPointUpdateResponse.of("200");
                })
                .orElseThrow(()->new NoSuchElementException("1002"));
    }
}
