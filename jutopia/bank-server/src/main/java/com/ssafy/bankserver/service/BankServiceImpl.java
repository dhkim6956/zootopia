package com.ssafy.bankserver.service;

import com.ssafy.bankserver.jpa.BankEntity;
import com.ssafy.bankserver.jpa.BankRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Data
@Slf4j
public class BankServiceImpl implements BankService{

    BankRepository bankRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository) {
        this.bankRepository = bankRepository;
    }

    @Override
    public Iterable<BankEntity> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public BankEntity getClassBanks(UUID classId) {
        return bankRepository.findByClassId(classId);
    }
}
