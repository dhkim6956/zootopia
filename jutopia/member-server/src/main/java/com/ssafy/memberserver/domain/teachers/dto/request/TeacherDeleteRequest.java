package com.ssafy.memberserver.domain.teachers.dto.request;

import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;
import io.swagger.v3.oas.annotations.media.Schema;

public record TeacherDeleteRequest(
        @Schema(description = "선생님 아이디")
        String teacherId,
        @Schema(description = "선생님 비밀번호")
        String teacherPwd,
        @Schema(description = "선생님 이름")
        String teacherName,
        @Schema(description = "Member의 권한")
        MemberRole memberRole,
        @Schema(description = "Member의 탈퇴 여부")
        MemberStatus memberStatus
) {
}
