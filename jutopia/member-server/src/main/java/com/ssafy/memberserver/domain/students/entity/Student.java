package com.ssafy.memberserver.domain.students.entity;

import com.ssafy.memberserver.common.enums.MemberBioStatus;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;
import com.ssafy.memberserver.domain.students.dto.request.StudentDeleteRequest;
import com.ssafy.memberserver.domain.students.dto.request.StudentUpdateRequest;
import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpRequest;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String studentId;
    private String studentPwd;
    private String studentName;
    private Integer point;
    private Integer money;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    MemberBioStatus memberBioStatus;
    @Enumerated(EnumType.STRING)
    MemberRole memberRole;
    @Enumerated(EnumType.STRING)
    MemberStatus memberStatus;

    public static Student from(StudentSignUpRequest studentSignUpRequest, PasswordEncoder passwordEncoder){
        return Student.builder()
                .studentId(studentSignUpRequest.studentId())
                .studentPwd(passwordEncoder.encode(studentSignUpRequest.studentPwd()))
                .studentName(studentSignUpRequest.studentName())
                .point(studentSignUpRequest.point())
                .money(studentSignUpRequest.money())
                .memberBioStatus(MemberBioStatus.INACTIVE)
                .createdAt(LocalDateTime.now())
                .updatedAt(studentSignUpRequest.updateTimeAt())
                .memberRole(MemberRole.STUDENT)
                .memberStatus(MemberStatus.ACTIVE)
                .build();
    }
    public void update(StudentUpdateRequest studentUpdateRequest, PasswordEncoder passwordEncoder){
        if(studentUpdateRequest.StudentNewPwd() != null || !studentUpdateRequest.StudentPwd().isBlank()){
            this.studentPwd = passwordEncoder.encode(studentUpdateRequest.StudentNewPwd());
        }
    }
    public void delete(StudentDeleteRequest studentDeleteRequest,PasswordEncoder passwordEncoder){
        if(studentDeleteRequest.memberStatus() == MemberStatus.ACTIVE){
            this.memberStatus = MemberStatus.INACTIVE;
        }
    }
}
