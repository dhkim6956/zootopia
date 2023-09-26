package com.ssafy.stockserver.domain.stock.entity;

import com.ssafy.stockserver.common.IndexType;
import com.ssafy.stockserver.domain.trading.entity.Trading;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Stock {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String stockNumber;
    private String stockFullnumber;
    private String country;
    private String market;
    private String sector;
    private String sectorCode;
    private IndexType indexType;
    @ManyToOne(fetch = FetchType.LAZY)
    Trading trading;

}
