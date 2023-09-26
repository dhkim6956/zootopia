//package com.ssafy.classserver.jpa;
//
//import jakarta.persistence.*;
//import lombok.Data;
//import org.hibernate.annotations.ColumnDefault;
//
//import java.io.Serializable;
//import java.math.BigDecimal;
//import java.util.UUID;
//
//@Entity
//@Data
//@Table(name = "bank")
//public class BankEntity implements Serializable {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.UUID)
//    private UUID id;
//
//    @Column(nullable = false)
//    private String bankName;
//    @ColumnDefault("0")
//    private BigDecimal money;
//    @Column(nullable = false)
//    private Double exchangeTaxRate;
//    @Column(nullable = false)
//    private Double consumptionTaxRate;
//    @Column(nullable = false)
//    private Double tradeTaxRate;
//    @Column(nullable = false)
//    private Double capitalGainsTaxRate;
//
//    // 어떤 반의 은행인지
//    @Column(nullable = false)
//    private UUID classId;
//}
