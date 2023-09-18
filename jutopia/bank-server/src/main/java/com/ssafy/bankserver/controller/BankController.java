package com.ssafy.bankserver.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/bank-server")
public class BankController {
    Environment env;

    @Autowired
    public BankController(Environment env) {
        this.env = env;
    }

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's working in Order Service on PORT %s", env.getProperty("local.server.port"));
    }


}
