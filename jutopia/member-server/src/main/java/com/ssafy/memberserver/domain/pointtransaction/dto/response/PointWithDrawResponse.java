package com.ssafy.memberserver.domain.pointtransaction.dto.response;

import com.ssafy.memberserver.domain.pointtransaction.entity.PointTransaction;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record PointWithDrawResponse(
        BigDecimal withDraw,
        String place,
        UUID studentId
) {
    public static PointWithDrawResponse from(PointTransaction pointTransaction){
        return PointWithDrawResponse.builder()
                .place(pointTransaction.getPlace())
                .withDraw(pointTransaction.getWithDraw())
                .build();
    }
}
