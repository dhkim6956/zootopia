package com.ssafy.teacher.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.teacher.dto.member.Member;
import com.ssafy.teacher.dto.member.TeacherRequest;
import com.ssafy.teacher.dto.rent.SeatRequest;
import com.ssafy.teacher.service.TeacherService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WebController {

    private final TeacherService service;


    @GetMapping("/login")
    public String login() throws JsonProcessingException {
        return "login";
    }

    @PostMapping("/login")
    public String loginPost(String memberId, String memberPwd, HttpSession session) {
        TeacherRequest request = TeacherRequest.builder()
                .memberId(memberId)
                .memberPwd(memberPwd)
                .build();
        Member user = service.login(request);
        if (user != null) {
            session.setAttribute("user", user);
            return "redirect:/dashboard";
        } else {
            return "login";
        }
    }


    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }

    @PostMapping("/createSeat")
    public String createSeat(@RequestParam int totalCount
            , @RequestParam int grade
            , @RequestParam int clazzNumber
            , HttpSession session) {
        Member user = (Member) session.getAttribute("user");
        SeatRequest request = SeatRequest.builder()
                .school(user.getSchool())
                .grade(grade)
                .clazzNumber(clazzNumber)
                .totalCount(totalCount)
                .build();
        service.createSeat(request);

        return "redirect:/dashboard";
    }
}
