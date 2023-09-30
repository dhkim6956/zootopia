package com.ssafy.stockserver.domain.memberStock.entity;

import com.ssafy.stockserver.domain.stock.entity.Stock;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Builder
@Entity
@Table(name = "memberstock")
public class MemberStock {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "MEMBERSTOCK_ID")
    private UUID id;

    // 누구의 거래 인지
    private UUID memberId;
    // 매입가
    private BigDecimal price;
    // 매입 수량
    private Long qty;
    // 거래일
    private LocalTime tradeAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_ID")
    private Stock stock;
}
