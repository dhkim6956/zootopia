package com.ssafy.classserver.jpa;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "savingproducts")
public class SavingProductsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private BigDecimal minMoney;
    @Column(nullable = false)
    private BigDecimal maxMoney;
    @Column(nullable = false)
    private Double InterestRate;
    @Column(nullable = false)
    private Short term;

    // 어느 은행의 적금 상품인지
    @Column(nullable = false)
    private UUID bankId;
}
