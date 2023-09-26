package com.ssafy.memberserver.domain.account.dto.response;

import com.ssafy.memberserver.common.enums.AccountStatus;
import com.ssafy.memberserver.common.enums.AccountType;
import com.ssafy.memberserver.domain.account.entity.Account;
import lombok.Builder;

@Builder
public record CreateAccountResponse(
        String accountName,
        String accountNumber,
        AccountType accountType,
        AccountStatus accountStatus
) {
    public static CreateAccountResponse from(Account account){
        return CreateAccountResponse.builder()
                .accountName(account.getAccountName())
                .accountNumber(account.getAccountNumber())
                .accountType(account.getAccountType())
                .accountStatus(account.getAccountStatus())
                .build();
    }
}
