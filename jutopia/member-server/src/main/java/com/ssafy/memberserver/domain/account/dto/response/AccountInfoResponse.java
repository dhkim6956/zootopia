package com.ssafy.memberserver.domain.account.dto.response;

import com.ssafy.memberserver.domain.account.entity.Account;
import com.ssafy.memberserver.domain.students.entity.Student;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record AccountInfoResponse(
        String accountName,
        String accountNumber,
        BigDecimal accountBalance,
        Student student
) {
    public static AccountInfoResponse from(Account account){
        return AccountInfoResponse.builder()
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .accountBalance(account.getAccountBalance())
                .build();
    }
}
