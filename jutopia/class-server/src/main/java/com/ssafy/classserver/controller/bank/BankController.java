package com.ssafy.classserver.controller.bank;


import com.ssafy.classserver.jpa.BankEntity;
import com.ssafy.classserver.service.BankService;
import com.ssafy.classserver.vo.response.ResponseBank;
import com.ssafy.common.api.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/class-server/api/banks")
public class BankController {
    Environment env;

    BankService bankService;

    @Autowired
    public BankController(Environment env, BankService bankService) {
        this.env = env;
        this.bankService = bankService;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in Order Service on PORT %s", env.getProperty("local.server.port"));
    }

    // 모든 은행 조회
    @GetMapping("/")
    public Api<List<ResponseBank>> getAllBanks() {
        Iterable<BankEntity> bankList = bankService.getAllBanks();

        List<ResponseBank> result = new ArrayList<>();

        bankList.forEach(c -> result.add(
                new ModelMapper().map(c, ResponseBank.class)
        ));
        return Api.OK(result);
    }

    // 해당 반의 은행 정보 가져오기
    @GetMapping("/banks/{classId}")
    public Api<ResponseBank> getClassBank(@PathVariable UUID classId) {
        BankEntity bank = bankService.getClassBanks(classId);

        ResponseBank result = new ModelMapper().map(bank, ResponseBank.class);

        return Api.OK(result);
    }
}
