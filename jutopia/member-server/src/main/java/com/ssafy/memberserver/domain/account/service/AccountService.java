package com.ssafy.memberserver.domain.account.service;

import com.ssafy.memberserver.domain.account.dto.request.AccountDeleteRequest;
import com.ssafy.memberserver.domain.account.dto.response.AccountDeleteResponse;
import com.ssafy.memberserver.domain.account.dto.response.AccountInfoResponse;
import com.ssafy.memberserver.domain.account.repository.AccountRepository;
import com.ssafy.memberserver.domain.students.entity.Student;
import com.ssafy.memberserver.domain.students.repository.StudentRepository;
import com.ssafy.memberserver.domain.students.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final StudentRepository studentRepository;
    @Transactional
    public AccountInfoResponse getAccountInfo(UUID id, String studentId) {
        return accountRepository.findById(id)
                .map(AccountInfoResponse::from)
                .orElseThrow(() -> new NoSuchElementException("test"));
    }
    @Transactional
    public AccountDeleteResponse deleteAccount(AccountDeleteRequest accountDeleteRequest, UUID id){
        return accountRepository.findById(id)
                .map(account -> {
                    account.delete(accountDeleteRequest);
                    return AccountDeleteResponse.of(true);
                })
                .orElseThrow(()-> new NoSuchElementException("없는 계좌입니다"));
    }
}
