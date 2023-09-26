package com.ssafy.stockserver.domain.volume.entity;

import com.ssafy.stockserver.domain.stock.entity.Stock;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@Getter
@Entity
public class Volume {
    @Id
    @GeneratedValue(strategy =GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime 샘플의시간크기;
    private LocalDateTime timestamp;
    private Long 외인보유량;
    private Long 외인순매수;
    private Long 기간보유량;
    private Long 기관보유비율;
    private Long 기관순매수량;
    private Long 개인보유량;
    private Long 개인보유비율;
    private Long 개인순매수량;
    private Long 총거래량;
    private BigDecimal 종가;
}
