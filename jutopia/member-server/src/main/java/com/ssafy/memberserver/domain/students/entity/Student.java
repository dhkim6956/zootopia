package com.ssafy.memberserver.domain.students.entity;

import com.ssafy.memberserver.common.enums.MemberBioStatus;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;
import com.ssafy.memberserver.common.enums.SeatOwnershipStatus;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointDepositRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointWithDrawRequest;
import com.ssafy.memberserver.domain.pointtransaction.dto.response.PointDepositResponse;
import com.ssafy.memberserver.domain.students.dto.request.*;
import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpRequest;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Entity
@Slf4j
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String studentId;
    private String studentPwd;
    private String studentName;
    private BigDecimal point;
    private BigDecimal money;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    @Enumerated(EnumType.STRING)
    MemberBioStatus memberBioStatus;
    @Enumerated(EnumType.STRING)
    MemberRole memberRole;
    @Enumerated(EnumType.STRING)
    MemberStatus memberStatus;
    @Enumerated(EnumType.STRING)
    SeatOwnershipStatus seatOwnershipStatus;
    private Integer school;
    private Integer grade;
    private Integer classRoom;

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
                .school(studentSignUpRequest.school())
                .grade(studentSignUpRequest.grade())
                .classRoom(studentSignUpRequest.classRoom())
                .seatOwnershipStatus(SeatOwnershipStatus.NOTOWNED)
                .build();
    }
    public void update(StudentUpdateRequest studentUpdateRequest, PasswordEncoder passwordEncoder){
        if(studentUpdateRequest.studentNewPwd() != null || !studentUpdateRequest.studentPwd().isBlank()){
            this.studentPwd = passwordEncoder.encode(studentUpdateRequest.studentNewPwd());
        }
    }
    public void pointUpdate(StudentPointUpdateRequest studentPointUpdateRequest){
        this.point = point.subtract(studentPointUpdateRequest.point());
    }
    public void addPointUpdate(PointDepositRequest pointDepositRequest, BigDecimal addPoint){
        if(pointDepositRequest.deposit() != null){
            this.point = addPoint;
        }
    }
    public void subtractPointUpdate(PointWithDrawRequest pointWithDrawRequest, BigDecimal subtractPoint){
        log.info("withDraw:{}",pointWithDrawRequest.withDraw());
        if(pointWithDrawRequest.withDraw() != null){
            this.point = subtractPoint;
        }
    }
    public void delete(StudentDeleteRequest studentDeleteRequest){
        if(studentDeleteRequest.memberStatus() == MemberStatus.ACTIVE){
            this.memberStatus = MemberStatus.INACTIVE;
        }
    }

    // feign -----------------------------------------------------------------
    public void memberPointUpdate(MemberPointUpdateRequest memberPointUpdateRequest){
        this.point = point.subtract(memberPointUpdateRequest.point());
    }
    public void memberMoneyUpdate(MemberMoneyUpdateRequest memberMoneyUpdateRequest){
        this.money = money.subtract(memberMoneyUpdateRequest.money());
    }
}
