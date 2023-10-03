package com.ssafy.memberserver.domain.students.sign.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.error.ErrorCode;
import com.ssafy.memberserver.common.exception.ApiException;
import com.ssafy.memberserver.domain.account.dto.request.CreateAccountRequest;
import com.ssafy.memberserver.domain.account.dto.response.CreateAccountResponse;
import com.ssafy.memberserver.domain.account.entity.Account;
import com.ssafy.memberserver.domain.account.repository.AccountRepository;
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

import java.util.NoSuchElementException;
import java.util.Optional;

@Slf4j
@RequiredArgsConstructor
@Service
public class StudentSignService {
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AccountRepository accountRepository;
    private final PasswordEncoder passwordEncoder;

//    Student student = studentRepository.findByStudentId(studentId)
//            .orElseThrow(() -> new NoSuchElementException("학생을 찾을 수 없습니다"));
//    Account account = accountRepository.save(Account.from(createAccountRequest,student));
//        accountRepository.flush();
    @Transactional
    public StudentSignUpResponse studentSignUp(CreateAccountRequest createAccountRequest,StudentSignUpRequest studentSignUpRequest) {
        if (studentRepository.findByStudentId(studentSignUpRequest.getStudentId()).isPresent()) {
            throw new ApiException(ErrorCode.BAD_REQUEST, "아이디가 중복입니다.");
        }
        Student student = studentRepository.save(Student.from(studentSignUpRequest, passwordEncoder));
        studentRepository.flush();
        CreateAccountNumber number = new CreateAccountNumber();
        Account account = accountRepository.save(Account.from(createAccountRequest,student, String.valueOf(number)));
        accountRepository.flush();
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
