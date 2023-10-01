package com.ssafy.memberserver.domain.history.dto.response;

import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.domain.history.entity.History;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;
@Builder
public record AccountHistoryResponse(
        Long id,
        BigDecimal amount,
        HistoryType historyType,
        UUID accountId
) {
    public static AccountHistoryResponse from(History history){
        return AccountHistoryResponse.builder()
                .id(history.getId())
                .amount(history.getAmount())
                .historyType(history.getHistoryType())
                .accountId(history.getAccount().getId())
                .build();
    }
}
