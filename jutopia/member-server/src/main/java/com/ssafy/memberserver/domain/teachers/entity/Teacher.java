package com.ssafy.memberserver.domain.teachers.entity;

import com.ssafy.memberserver.common.enums.MemberBioStatus;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;
import com.ssafy.memberserver.domain.teachers.dto.request.TeacherDeleteRequest;
import com.ssafy.memberserver.domain.teachers.dto.request.TeacherUpdateRequest;
import com.ssafy.memberserver.domain.teachers.sign.dto.SignUp.request.TeacherSignUpRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
@Getter
@Entity
public class Teacher {
    //TODO: validation 필요
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String teacherId;
    private String teacherPwd;
    private String teacherName;
    private String teacherEmail;
    @Enumerated(EnumType.STRING)
    private MemberRole memberRole;
    @Enumerated(EnumType.STRING)
    private MemberStatus memberStatus;
    @Enumerated(EnumType.STRING)
    private MemberBioStatus memberBioStatus;

    public static Teacher from(TeacherSignUpRequest teacherSignUpRequest, PasswordEncoder passwordEncoder){
        return Teacher.builder()
                .teacherId(teacherSignUpRequest.teacherId())
                .teacherPwd(passwordEncoder.encode(teacherSignUpRequest.teacherPwd()))
                .teacherName(teacherSignUpRequest.Name())
                .teacherEmail(teacherSignUpRequest.teacherEmail())
                .memberRole(MemberRole.TEACHER)
                .memberStatus(MemberStatus.ACTIVE)
                .memberBioStatus(MemberBioStatus.INACTIVE)
                .build();
    }
    //TODO:삼항 연사자 변경 필요
    public void update(TeacherUpdateRequest teacherUpdateRequest,PasswordEncoder passwordEncoder){
        if(teacherUpdateRequest.teacherNewPwd() != null || !teacherUpdateRequest.teacherPwd().isBlank()){
            this.teacherPwd = passwordEncoder.encode(teacherUpdateRequest.teacherNewPwd());
        }
    }
    public void delete(TeacherDeleteRequest teacherDeleteRequest,PasswordEncoder passwordEncoder){
        if(teacherDeleteRequest.memberStatus() == MemberStatus.ACTIVE){
            this.memberStatus = MemberStatus.INACTIVE;
        }
    }
}
