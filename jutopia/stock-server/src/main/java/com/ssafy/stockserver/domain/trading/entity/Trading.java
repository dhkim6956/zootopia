package com.ssafy.stockserver.domain.trading.entity;

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
public class Trading {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "TRADING_ID")
    private UUID id;

    private BigDecimal price;
    private LocalTime tradeAt;

    //@ManyToOne
    //    private Student student;

}
