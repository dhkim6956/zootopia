package com.ssafy.bankserver.domain.account.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
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


//    @GetMapping(value = "/api/accounts/{userId}", produces = {MediaType.APPLICATION_JSON_VALUE})
//    public ResponseEntity<ResponseUser> getAccounts(@PathVariable("userId") String userId) {
//        UserDto userDto = userService.getUserByUserId(userId);
//        ResponseUser returnValue = new ModelMapper().map(userDto, ResponseUser.class);
//
//        return ResponseEntity.status(HttpStatus.OK).body(returnValue);
//    }
}
