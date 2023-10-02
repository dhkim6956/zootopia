package com.ssafy.memberserver.domain.students.sign.dto.signUp;

import com.ssafy.memberserver.common.enums.MemberBioStatus;
import com.ssafy.memberserver.common.enums.MemberRole;
import com.ssafy.memberserver.common.enums.MemberStatus;
import com.ssafy.memberserver.common.enums.SeatOwnershipStatus;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
public class StudentSignUpRequest {
    private String studentId;
    private String studentPwd;
    private String studentName;
    private String school;
    private Integer grade;
    private Integer classRoom;
    private Integer studentNumber;
    private SeatOwnershipStatus seatOwnershipStatus;
}
