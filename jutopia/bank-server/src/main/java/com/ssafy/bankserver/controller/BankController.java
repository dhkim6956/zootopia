package com.ssafy.bankserver.controller;


import com.ssafy.bankserver.jpa.BankEntity;
import com.ssafy.bankserver.service.BankService;
import com.ssafy.bankserver.vo.response.ResponseBank;
import com.ssafy.common.api.Api;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/bank-server/api")
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

    @GetMapping("/banks")
    public ResponseEntity<List<ResponseBank>> getAllBanks() {
        Iterable<BankEntity> bankList = bankService.getAllBanks();

        List<ResponseBank> result = new ArrayList<>();

        bankList.forEach(c -> result.add(
                new ModelMapper().map(c, ResponseBank.class)
        ));
        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/banks/{classId}")
    public Api<ResponseBank> getClassBank(@PathVariable UUID classId) {
        BankEntity bank = bankService.getClassBanks(classId);

        ResponseBank result = new ModelMapper().map(bank, ResponseBank.class);

        return Api.OK(result);

    }
}
