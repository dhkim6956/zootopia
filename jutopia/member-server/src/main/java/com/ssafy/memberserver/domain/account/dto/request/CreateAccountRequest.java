package com.ssafy.memberserver.domain.account.dto.request;

import com.ssafy.memberserver.common.enums.AccountStatus;
import com.ssafy.memberserver.common.enums.AccountType;
import com.ssafy.memberserver.common.enums.MoneyType;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.UUID;

@Getter
public class CreateAccountRequest {
    private String accountName;
    private String accountNumber;
    private BigDecimal accountBalance;
    private AccountType accountType;
    private AccountStatus accountStatus;
    private MoneyType moneyType;
    private UUID studentId;
}

