package com.ssafy.memberserver.domain.students.sign.dto.signUp;

import com.ssafy.memberserver.common.enums.MemberBioStatus;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record StudentSignUpRequest(
        String studentId,
        String studentPwd,
        String studentName,
        BigDecimal point,
        BigDecimal money,
        MemberBioStatus memberBioStatus,
        LocalDateTime createTimeAt,
        LocalDateTime updateTimeAt,
        MemberRole memberRole,
        MemberStatus memberStatus,
        Integer school,
        Integer grade,
        Integer classRoom
) {
}
