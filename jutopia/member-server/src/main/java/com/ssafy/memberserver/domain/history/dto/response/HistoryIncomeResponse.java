package com.ssafy.memberserver.domain.history.dto.response;

import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.domain.history.entity.History;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record HistoryIncomeResponse(
        Long id,
        BigDecimal amount,
        HistoryType historyType,
        UUID accountId
) {
    public static HistoryIncomeResponse from(History history){
        return HistoryIncomeResponse.builder()
                .id(history.getId())
                .amount(history.getAmount())
                .historyType(history.getHistoryType())
                .accountId(history.getAccount().getId())
                .build();
    }
}
