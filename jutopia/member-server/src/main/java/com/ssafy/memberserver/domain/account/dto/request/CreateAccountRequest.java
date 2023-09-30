package com.ssafy.memberserver.domain.account.dto.request;

import com.ssafy.memberserver.common.enums.AccountStatus;
import com.ssafy.memberserver.common.enums.AccountType;
import com.ssafy.memberserver.common.enums.MoneyType;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateAccountRequest(
        String accountName,
        String accountNumber,
        BigDecimal accountBalance,
        AccountType accountType,
        AccountStatus accountStatus,
        MoneyType moneyType,
        UUID studentId
) {
}
