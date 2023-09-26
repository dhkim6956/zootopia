package com.ssafy.memberserver.domain.pointtransaction.dto.response;

import com.ssafy.memberserver.domain.pointtransaction.dto.request.PointDepositRequest;
import com.ssafy.memberserver.domain.pointtransaction.entity.PointTransaction;
import lombok.Builder;

import java.math.BigDecimal;

@Builder
public record PointDepositResponse(
        String place,
        BigDecimal deposit
) {
    public static PointDepositResponse from(PointTransaction pointTransaction){
        return PointDepositResponse.builder()
                .deposit(pointTransaction.getDeposit())
                .place(pointTransaction.getPlace())
                .build();

    }
}
