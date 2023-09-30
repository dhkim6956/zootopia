package com.ssafy.memberserver.domain.account.dto.request;

import com.ssafy.memberserver.common.enums.AccountStatus;
import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.common.enums.MoneyType;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record SendMoneyRequest(
        UUID id,
        String sender,
        String receiver,
        BigDecimal amount,
        MoneyType moneyType,
        HistoryType historyType,
        AccountStatus accountStatus,
        LocalDateTime createdAt,
        String studentId
) {
}
