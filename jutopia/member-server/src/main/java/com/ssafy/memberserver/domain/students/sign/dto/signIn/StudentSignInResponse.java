package com.ssafy.memberserver.domain.students.sign.dto.signIn;

import com.ssafy.memberserver.common.enums.MemberBioStatus;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;
import com.ssafy.memberserver.common.enums.SeatOwnershipStatus;
import com.ssafy.memberserver.domain.students.entity.Student;
import com.ssafy.memberserver.domain.students.sign.dto.signUp.StudentSignUpResponse;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record StudentSignInResponse(
        UUID id,
        String studentId,
        String name,
        MemberBioStatus memberBioStatus,
        BigDecimal money,
        BigDecimal point,
        MemberRole memberRole,
        MemberStatus memberStatus,
        String school,
        int grade,
        int classroom,
        int studentNumber,
        SeatOwnershipStatus seatOwnershipStatus,
        String token
) {
    public static StudentSignInResponse from(Student student, String token){
        return StudentSignInResponse.builder()
                .id(student.getId())
                .studentId(student.getStudentId())
                .name(student.getStudentName())
                .memberStatus(student.getMemberStatus())
                .memberRole(student.getMemberRole())
                .memberBioStatus(student.getMemberBioStatus())
                .money(student.getMoney())
                .point(student.getPoint())
                .school(student.getSchool())
                .grade(student.getGrade())
                .classroom(student.getClassRoom())
                .studentNumber(student.getStudentNumber())
                .seatOwnershipStatus(student.getSeatOwnershipStatus())
                .token(token)
                .build();
    }
}
