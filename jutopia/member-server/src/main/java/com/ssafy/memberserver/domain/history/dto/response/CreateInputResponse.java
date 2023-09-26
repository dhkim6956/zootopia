package com.ssafy.memberserver.domain.history.dto.response;

import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.domain.history.entity.History;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record CreateInputResponse(
        Long id,
        BigDecimal amount,
        String list,
        HistoryType historyType,
        UUID accountId
) {
    public static CreateInputResponse from(History history){
        return CreateInputResponse.builder()
                .id(history.getId())
                .amount(history.getAmount())
                .list(history.getList())
                .historyType(history.getHistoryType())
                .build();
    }
}
