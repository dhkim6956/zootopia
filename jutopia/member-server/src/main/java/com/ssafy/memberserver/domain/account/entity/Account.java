package com.ssafy.memberserver.domain.account.entity;

import com.ssafy.memberserver.common.enums.AccountStatus;
import com.ssafy.memberserver.common.enums.AccountType;
import com.ssafy.memberserver.common.enums.MoneyType;
import com.ssafy.memberserver.domain.account.dto.request.AccountDeleteRequest;
import com.ssafy.memberserver.domain.account.dto.request.CreateAccountRequest;
import com.ssafy.memberserver.domain.students.entity.Student;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Builder
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

    public static Account from(CreateAccountRequest createAccountRequest,Student student){
        return Account.builder()
                .accountName(createAccountRequest.accountName())
                .accountNumber(createAccountRequest.accountName())
                .accountType(createAccountRequest.accountType())
//                .moneyType(createAccountRequest.)
                .student(student)
                .accountStatus(AccountStatus.ACTIVE)
                .build();

    }

    //TODO: 이거 왜 안됨?
    public void delete(AccountDeleteRequest accountDeleteRequest){
        if(accountDeleteRequest.accountStatus() == AccountStatus.ACTIVE){
            this.accountStatus = AccountStatus.INACTIVE;
        }
    }
}
