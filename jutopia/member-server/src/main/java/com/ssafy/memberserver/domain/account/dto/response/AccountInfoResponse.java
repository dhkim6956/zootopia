package com.ssafy.memberserver.domain.account.dto.response;

import com.ssafy.memberserver.common.enums.AccountType;
import com.ssafy.memberserver.common.enums.MoneyType;
import com.ssafy.memberserver.domain.account.entity.Account;
import com.ssafy.memberserver.domain.students.entity.Student;
import lombok.Builder;

@Builder
public record AccountInfoResponse(
        String accountName,
        String accountNumber,
        AccountType accountType,
        MoneyType moneyType,
        Student student
) {
    public static AccountInfoResponse from(Account account){
        return AccountInfoResponse.builder()
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .moneyType(account.getMoneyType())
                .student(account.getStudent())
                .build();

    }
}
