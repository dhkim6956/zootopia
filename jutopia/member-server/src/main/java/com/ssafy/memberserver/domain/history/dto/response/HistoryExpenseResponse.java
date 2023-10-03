package com.ssafy.memberserver.domain.history.dto.response;

import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.domain.history.entity.History;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record HistoryExpenseResponse(
        Long id,
        String sender,
        String receiver,
        BigDecimal amount,
        HistoryType historyType,
        UUID accountId
) {
    public static HistoryExpenseResponse from(History history){
        return HistoryExpenseResponse.builder()
                .id(history.getId())
                .sender(history.getSender())
                .receiver(history.getReceiver())
                .amount(history.getAmount())
                .historyType(history.getHistoryType())
                .accountId(history.getAccount().getId())
                .build();
    }
}
