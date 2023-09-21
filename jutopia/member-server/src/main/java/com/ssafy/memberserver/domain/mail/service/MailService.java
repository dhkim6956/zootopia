package com.ssafy.memberserver.domain.mail.service;

import com.ssafy.memberserver.common.config.RedisService;
import com.ssafy.memberserver.domain.teachers.entity.Teacher;
import com.ssafy.memberserver.domain.teachers.repository.TeacherRepository;
import com.ssafy.memberserver.domain.teachers.sign.dto.SignUp.request.TeacherSignUpRequest;
import com.ssafy.memberserver.domain.teachers.sign.service.TeacherSignService;
import com.ssafy.memberserver.common.config.RedisService;
import com.ssafy.memberserver.domain.teachers.repository.TeacherRepository;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.ChangedCharSetException;
import java.io.UnsupportedEncodingException;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Optional;
import java.util.Random;

@Service
@Slf4j
@Transactional
@RequiredArgsConstructor
public class MailService {
    private final JavaMailSender javaMailSender; // Bean 등록해둔 MailConfig 를 emailsender 라는 이름으로 autowired
    private final RedisService redisService;
    private final TeacherRepository teacherRepository;
    public static final String code = createCode(); // 인증번호
    // 메일 내용 작성
    //TODO: 검증 및 아이디 중복 체크 및 로직 수정 필요함(레디스에서 익스파이어가 안됨)

    public MimeMessage createMessage(String to) throws MessagingException, UnsupportedEncodingException {
            log.info("이메일 입니다:"+"{}",to);
            if(checkDuplicatedEmail(to)) {
                MimeMessage message = javaMailSender.createMimeMessage();
                ;
                message.addRecipients(Message.RecipientType.TO, to);// 보내는 대상
                message.setSubject("주토피아 이메일 인증");// 제목
                String msgg = "";
                msgg += "<div style='margin:100px;'>";
                msgg += "<h1> 안녕하세요</h1>";
                msgg += "<h1> 주토피아 입니다.</h1>";
                msgg += "CODE : <strong>";
                msgg += code + "</strong><div><br/> ";
                msgg += "</div>";
                message.setText(msgg, "utf-8", "html");// 내용, charset 타입, subtype
                // 보내는 사람의 이메일 주소, 보내는 사람 이름
                message.setFrom(new InternetAddress("kotworld1004@naver.com", "소영섭"));// 보내는 사람
                return message;
            }else{
                return null;
            }
    }

    // 랜덤 인증 코드 생성
    public static String createCode() {
        int codeLength = 6;
        try {
            Random random = SecureRandom.getInstanceStrong();
            StringBuilder code = new StringBuilder();
            for (int i = 0; i < codeLength; i++) {
                code.append(random.nextInt(10));
            }
            return code.toString();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("생성 오류");
        }
    }

    // 메일 발송
    // sendSimpleMessage 의 매개변수로 들어온 to 는 곧 이메일 주소가 되고,
    // MimeMessage 객체 안에 내가 전송할 메일의 내용을 담는다.
    // 그리고 bean 으로 등록해둔 javaMail 객체를 사용해서 이메일 send!!

    public String sendSimpleMessage(String to) throws Exception {
        // 랜덤 인증번호 생성
        redisService.setValuesWithTimeout(code,to,60 * 1L);
        MimeMessage message = createMessage(to); // 메일 발송
        try {// 예외처리
            javaMailSender.send(message);
        } catch (MailException es) {
            es.printStackTrace();
            throw new IllegalArgumentException();
        }
        return code; // 메일로 보냈던 인증 코드를 서버로 반환
    }
    private boolean checkDuplicatedEmail(String email){
        Optional<Teacher> teacher = teacherRepository.findByTeacherEmail(email);
        if(teacher.isPresent()){
            return false;
        } else{
            return true;
        }
    }
    public String verifyEmail(String key) throws ChangeSetPersister.NotFoundException{
        String teacherEmail = redisService.getValues(key);
        if(teacherEmail == null){
            throw new ChangeSetPersister.NotFoundException();
        }
        redisService.deleteValues(key);
        return code;
    }
}
