package com.ssafy.memberserver.domain.history.service;

import com.ssafy.memberserver.domain.account.entity.Account;
import com.ssafy.memberserver.domain.account.repository.AccountRepository;
import com.ssafy.memberserver.domain.history.dto.request.CreateInputRequest;
import com.ssafy.memberserver.domain.history.dto.response.CreateInputResponse;
import com.ssafy.memberserver.domain.history.dto.response.HistoryResponse;
import com.ssafy.memberserver.domain.history.entity.History;
import com.ssafy.memberserver.domain.history.repository.HistoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class HistoryService {
    private final HistoryRepository historyRepository;
    private final AccountRepository accountRepository;

    @Transactional
    public List<History> getAllHistoryByAccountId(UUID accountId){
        return historyRepository.findByAccountId(accountId);
    }
    @Transactional
    public CreateInputResponse createInput(CreateInputRequest createInputRequest, UUID id){
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("계좌 못찾음"));
        History history = historyRepository.save(History.from(createInputRequest,account));
        historyRepository.flush();
        return CreateInputResponse.from(history);
    }
}
