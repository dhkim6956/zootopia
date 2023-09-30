package com.ssafy.stockserver.domain.memberStock.service;

import com.ssafy.stockserver.domain.memberStock.entity.MemberStock;
import com.ssafy.stockserver.domain.memberStock.repository.MemberStockRepository;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Data
@Slf4j
public class MemberStockServiceImpl implements MemberStockService{

    MemberStockRepository memberStockRepository;

    @Autowired
    public MemberStockServiceImpl(MemberStockRepository memberStockRepository) {
        this.memberStockRepository = memberStockRepository;
    }

    @Override
    public Iterable<MemberStock> getMemberStock(UUID memberId) {
        return memberStockRepository.findAllByMemberId(memberId);
    }
}
