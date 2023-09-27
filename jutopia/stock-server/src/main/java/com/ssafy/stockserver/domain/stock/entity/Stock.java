package com.ssafy.stockserver.domain.stock.entity;

import com.ssafy.stockserver.common.IndexType;
import com.ssafy.stockserver.domain.trading.entity.Trading;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "")
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "STOCK_ID")
    private UUID id;

    private String stockCode;
    private String stockName;

    private String stockFullnumber;

    private String country;
    private String market;
    private String sector;
    private String sectorCode;
    private IndexType indexType;

//    @OneToMany(fetch = FetchType.LAZY)
//    Trading trading;
}
