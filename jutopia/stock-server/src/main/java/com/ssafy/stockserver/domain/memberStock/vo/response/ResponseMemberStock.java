package com.ssafy.stockserver.domain.memberStock.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseMemberStock {
    private UUID id;

    private UUID stockId;
    private String stockName;
    private String stockCode;

    // 총 보유 수량
    private Long qty;
    // 총 보유 금액
    private BigDecimal totalPrice;
    // 평단가
    private BigDecimal avgPrice;

}
