package com.ssafy.teacher.dto.member;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
@Setter
public class Member {
    private UUID id;
    private String memberId;
    private String name;
    private String memberBioStatus;
    private BigDecimal money;
    private BigDecimal point;
    private String memberRole;
    private String memberStatus;
    private String school;
    private String grade;
    private String classroom;
    private String studentNumber;
    private String seatOwnershipStatus;
    private String token;
}
