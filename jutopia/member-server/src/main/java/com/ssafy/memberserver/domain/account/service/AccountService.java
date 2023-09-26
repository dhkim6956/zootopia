package com.ssafy.memberserver.domain.account.service;

import com.ssafy.memberserver.domain.account.dto.request.AccountDeleteRequest;
import com.ssafy.memberserver.domain.account.dto.request.CreateAccountRequest;
import com.ssafy.memberserver.domain.account.dto.response.AccountDeleteResponse;
import com.ssafy.memberserver.domain.account.dto.response.AccountInfoResponse;
import com.ssafy.memberserver.domain.account.dto.response.CreateAccountResponse;
import com.ssafy.memberserver.domain.account.entity.Account;
import com.ssafy.memberserver.domain.account.repository.AccountRepository;
import com.ssafy.memberserver.domain.students.entity.Student;
import com.ssafy.memberserver.domain.students.repository.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final StudentRepository studentRepository;
    @Transactional
    public AccountInfoResponse getAccountInfo(UUID id, String studentId) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("계좌를 찾을 수 없습니다"));
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NoSuchElementException("학생을 찾을 수 없습니다"));
        if (!account.getStudent().equals(student)) {
            throw new NoSuchElementException("학생 계좌 정보가 일치하지 않습니다");
        }
        return AccountInfoResponse.from(account);
    }
    @Transactional
    public CreateAccountResponse createAccount(CreateAccountRequest createAccountRequest,String studentId){
        Student student = studentRepository.findByStudentId(studentId)
                .orElseThrow(() -> new NoSuchElementException("학생을 찾을 수 없습니다"));
        Account account = accountRepository.save(Account.from(createAccountRequest,student));
        accountRepository.flush();
        return CreateAccountResponse.from(account);
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
