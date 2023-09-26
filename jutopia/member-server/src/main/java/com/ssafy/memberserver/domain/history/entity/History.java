package com.ssafy.memberserver.domain.history.entity;

import com.ssafy.memberserver.common.enums.HistoryType;
import com.ssafy.memberserver.domain.account.entity.Account;
import com.ssafy.memberserver.domain.history.dto.request.CreateInputRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal amount;
    private String list;
    @Enumerated(EnumType.STRING)
    private HistoryType historyType;
    @ManyToOne
    private Account account;

    public static History from(CreateInputRequest historyRequest, Account account){
        return History.builder()
                .id(historyRequest.id())
                .amount(historyRequest.amount())
                .list(historyRequest.list())
                .historyType(historyRequest.historyType())
                .account(account)
                .build();
    }
}
