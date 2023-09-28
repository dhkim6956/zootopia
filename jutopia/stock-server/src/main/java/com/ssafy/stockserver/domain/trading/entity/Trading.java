package com.ssafy.stockserver.domain.trading.entity;

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
@Table(name = "trading")
public class Trading {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TRADING_ID")
    private UUID id;

    // 누구의 거래 인지
    private UUID memberId;

    private BigDecimal price;
    private LocalTime tradeAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "STOCK_ID")
    private Stock stock;
}
