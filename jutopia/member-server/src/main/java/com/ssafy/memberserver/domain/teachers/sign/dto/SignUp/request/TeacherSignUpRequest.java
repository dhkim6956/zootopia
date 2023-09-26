package com.ssafy.memberserver.domain.teachers.sign.dto.SignUp.request;

import com.ssafy.memberserver.common.enums.MemberBioStatus;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;

import java.util.UUID;

public record TeacherSignUpRequest(
        UUID id,
        String teacherId,
        String teacherPwd,
        String Name,
        String teacherEmail,
        MemberRole memberRole,
        MemberStatus memberStatus,
        MemberBioStatus memberBioStatus
) {
}
