package com.ssafy.memberserver.domain.account.entity;

import com.ssafy.memberserver.common.enums.AccountStatus;
import com.ssafy.memberserver.common.enums.AccountType;
import com.ssafy.memberserver.common.enums.MoneyType;
import com.ssafy.memberserver.domain.account.dto.request.AccountDeleteRequest;
import com.ssafy.memberserver.domain.students.entity.Student;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String accountName;
    private String accountNumber;
    @Enumerated(EnumType.STRING)
    private AccountType accountType;
    @Enumerated(EnumType.STRING)
    private MoneyType moneyType;
    @Enumerated(EnumType.STRING)
    private AccountStatus accountStatus;
    @ManyToOne(fetch = FetchType.LAZY)
    private Student student;
    

    //TODO: 어떤 적금의 탈퇴 여부
    public void delete(AccountDeleteRequest accountDeleteRequest){
        if(accountDeleteRequest.accountStatus() == AccountStatus.ACTIVE){
            this.accountStatus = AccountStatus.INACTIVE;
        }
    }
}
