package com.ssafy.teacher.service;

import com.ssafy.teacher.client.MemberClient;
import com.ssafy.teacher.client.RentClient;
import com.ssafy.teacher.dto.member.Member;
import com.ssafy.teacher.dto.member.TeacherRequest;
import com.ssafy.teacher.dto.rent.SeatRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TeacherService {

    private final MemberClient memberClient;
    private final RentClient rentClient;

    public String login(TeacherRequest request){
        Member user = memberClient.login(request);
        return "1";
    }

    public String createSeat(SeatRequest request){

        var res = rentClient.createSeats(request);

        return res;
    }
}
