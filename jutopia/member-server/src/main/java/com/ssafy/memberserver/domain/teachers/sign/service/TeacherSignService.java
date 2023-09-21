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
    //TODO: email 구현 서비스 파트
//    private void checkDuplicatedEmail(String email){
//        Optional<Teacher> teacher = Optional.ofNullable(teacherRepository.findByTeacherEmail(email))
//                .filter(it -> it.isPresent())
//                .orElseThrow(() -> new IllegalArgumentException("존재하는 이메일 입니다"));
//    }
//
//    private String createCode(){
//        int length = 6;
//        try{
//            Random random = SecureRandom.getInstanceStrong();
//            StringBuilder sb = new StringBuilder();
//            for (int i = 0; i < length; i++) {
//                sb.append(random.nextInt(10));
//            }
//            return sb.toString();
//        }catch (NoSuchAlgorithmException e){
//            throw new IllegalArgumentException("생성 오류");
//        }
//    }
//    public EmailVerificationResult verifiedCode(String email, String authCode){
//        this.checkDuplicatedEmail(email);
//        String redisAuthCode = redisService.getValues(AUTH_CODE_PREFIX + email);
//        boolean
//    }
}
