package com.ssafy.memberserver.domain.students.sign.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.memberserver.common.api.Api;
import com.ssafy.memberserver.common.error.ErrorCode;
import com.ssafy.memberserver.common.exception.ApiException;
import com.ssafy.memberserver.domain.security.TokenProvider;
import com.ssafy.memberserver.domain.students.entity.Student;
import com.ssafy.memberserver.domain.students.repository.StudentRepository;
import com.ssafy.memberserver.domain.students.sign.dto.signIn.StudentSignInRequest;
import com.ssafy.memberserver.domain.students.sign.dto.signIn.StudentSignInResponse;
import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpRequest;
import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpResponse;
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
    private final PasswordEncoder passwordEncoder;
    private final TokenProvider tokenProvider;
    @Transactional
    public StudentSignUpResponse studentSignUp(StudentSignUpRequest studentSignUpRequest) {
        if (studentRepository.findByStudentId(studentSignUpRequest.getStudentId()).isPresent()) {
            throw new ApiException(ErrorCode.BAD_REQUEST,"아이디가 중복입니다.");
        }
        Student student = studentRepository.save(Student.from(studentSignUpRequest, passwordEncoder));
        studentRepository.flush();
        return StudentSignUpResponse.from(student);
    }
    @Transactional
    public StudentSignInResponse studentSignIn(StudentSignInRequest studentSignInRequest) throws JsonProcessingException {
        // test
//        Student student =
                Optional.ofNullable(studentRepository.findByStudentId(studentSignInRequest.getStudentId()))
                .orElseThrow(()-> new ApiException(ErrorCode.STUDENT_INVALID_INPUT,"존재하지 않는 아이디입니다."))
                .filter(student -> passwordEncoder.matches(studentSignInRequest.getStudentPwd(),student.getStudentPwd()))
                .orElseThrow(() -> new ApiException(ErrorCode.STUDENT_INVALID_INPUT,"비밀번호가 틀렸습니다"));
                String accessToken = tokenProvider.createAccessToken("test");
                log.info("accessToken:"+"{}",accessToken);
                log.info("decodeJwtPayloadSubject:"+"{}",tokenProvider.decodeJwtPayloadSubject(accessToken));
        return new StudentSignInResponse();
    }
    public boolean checkStudentIdDuplicated(String studentId){
        return studentRepository.existsByStudentId(studentId);
    }
}
