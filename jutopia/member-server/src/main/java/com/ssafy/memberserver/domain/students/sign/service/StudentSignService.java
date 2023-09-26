package com.ssafy.memberserver.domain.students.sign.service;

import com.fasterxml.jackson.core.JsonProcessingException;
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
    public StudentSignUpResponse studentSignUp(StudentSignUpRequest studentSignUpRequest){
        Student student = studentRepository.save(Student.from(studentSignUpRequest,passwordEncoder));
        studentRepository.flush();
        return StudentSignUpResponse.from(student);
    }
    @Transactional
    public StudentSignInResponse studentSignIn(StudentSignInRequest studentSignInRequest) throws JsonProcessingException {
//        Student student =
                Optional.ofNullable(studentRepository.findByStudentId(studentSignInRequest.StudentId()))
                .orElseThrow(()-> new IllegalArgumentException("존재하지 않는 아이디입니다."))
                .filter(student -> passwordEncoder.matches(studentSignInRequest.StudentPwd(),student.getStudentPwd()))
                .orElseThrow(() -> new IllegalArgumentException("비밀번호가 틀렸습니다"));
                String accessToken = tokenProvider.createAccessToken("test");
                log.info("accessToken:"+"{}",accessToken);
                log.info("decodeJwtPayloadSubject:"+"{}",tokenProvider.decodeJwtPayloadSubject(accessToken));
        return new StudentSignInResponse();
    }
    public boolean checkStudentIdDuplicated(String studentId){
        return studentRepository.existsByStudentId(studentId);
    }
}
