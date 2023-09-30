package com.ssafy.memberserver.domain.teachers.sign.service;

import com.ssafy.memberserver.domain.teachers.entity.Teacher;
import com.ssafy.memberserver.domain.teachers.repository.TeacherRepository;
import com.ssafy.memberserver.domain.teachers.sign.dto.SignIn.request.TeacherSignInRequest;
import com.ssafy.memberserver.domain.teachers.sign.dto.SignIn.response.TeacherSignInResponse;
import com.ssafy.memberserver.domain.teachers.sign.dto.SignUp.request.TeacherSignUpRequest;
import com.ssafy.memberserver.domain.teachers.sign.dto.SignUp.response.TeacherSignUpResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class TeacherSignService {
    private final TeacherRepository teacherRepository;
    private final PasswordEncoder passwordEncoder;
    @Transactional
    public TeacherSignUpResponse teacherSignUp(TeacherSignUpRequest teacherSignUpRequest){
        if (teacherRepository.findByTeacherId(teacherSignUpRequest.teacherId()).isPresent()){
            throw new IllegalStateException("아이디 중복입니다.");
        }
        Teacher teacher = teacherRepository.save(Teacher.from(teacherSignUpRequest, passwordEncoder));
        teacherRepository.flush();
        return TeacherSignUpResponse.from(teacher);
    }
    @Transactional
    public TeacherSignInResponse teacherSignIn(TeacherSignInRequest teacherSignInRequest){
        //Teacher teacher =
                Optional.ofNullable(teacherRepository.findByTeacherId(teacherSignInRequest.teacherId()))
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 아이디 입니다."))
                .filter(teacher -> passwordEncoder.matches(teacherSignInRequest.teacherPwd(),teacher.getTeacherPwd()))
                .orElseThrow(() -> new IllegalArgumentException("비밀번호가 틀렸습니다."));
        return new TeacherSignInResponse();
    }
    public boolean checkTeacherIdDuplicated(String teacherId){
        return teacherRepository.existsByTeacherId(teacherId);
    }
}
