package com.ssafy.memberserver.domain.students.dto.response;

import com.ssafy.memberserver.common.enums.MemberBioStatus;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;
import com.ssafy.memberserver.domain.students.entity.Student;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Builder
public record StudentInfoResponse(
        @Schema(description = "학생 고유키")
        UUID id,
        @Schema(description = "학생 아이디")
        String studentId,
        @Schema(description = "학생 비밀 번호")
        String studentPwd,
        @Schema(description = "학생 이름")
        String studentName,
        @Schema(description = "학생 포인트")
        BigDecimal point,
        @Schema(description = "학생 화폐 총량")
        BigDecimal money,
        @Schema(description = "학생 생성일")
        LocalDateTime createdAt,
        @Schema(description = "학생 수정일")
        LocalDateTime updateAt,
        @Schema(description = "학생 권한")
        MemberRole memberRole,
        @Schema(description = "학생 상태 여부")
        MemberStatus memberStatus,
        @Schema(description = "학생 바이오 여부")
        MemberBioStatus memberBioStatus,
        Integer school,
        Integer grade,
        Integer classRoom
) {
        public static StudentInfoResponse from(Student student){
                return StudentInfoResponse.builder()
                        .id(student.getId())
                        .studentId(student.getStudentId())
                        .studentPwd(student.getStudentPwd())
                        .studentName(student.getStudentName())
                        .point(student.getPoint())
                        .money(student.getMoney())
                        .createdAt(student.getCreatedAt())
                        .updateAt(student.getUpdatedAt())
                        .memberRole(student.getMemberRole())
                        .memberStatus(student.getMemberStatus())
                        .memberBioStatus(student.getMemberBioStatus())
                        .school(student.getSchool())
                        .grade(student.getGrade())
                        .classRoom(student.getClassRoom())
                        .build();
        }
}
