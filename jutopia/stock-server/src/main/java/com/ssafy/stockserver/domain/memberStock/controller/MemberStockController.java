package com.ssafy.stockserver.domain.memberStock.controller;

import com.ssafy.common.api.Api;
import com.ssafy.stockserver.domain.memberStock.entity.MemberStock;
import com.ssafy.stockserver.domain.memberStock.service.MemberStockService;
import com.ssafy.stockserver.domain.memberStock.vo.response.ResponseMemberStock;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.stock.service.StockService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/stock-server/api/memberstock")
@Tag(name = "memberstock", description = "회원 보유 주식 조회")
public class MemberStockController {

    MemberStockService memberStockService;
    StockService stockService;
    ModelMapper mapper;

    @Autowired
    public MemberStockController(MemberStockService memberStockService, StockService stockService) {
        this.memberStockService = memberStockService;
        this.stockService = stockService;
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @GetMapping("/{memberId}")
    public Api<List<ResponseMemberStock>> getMemberStocks(@PathVariable UUID memberId) {
        Iterable<MemberStock> memberStockList = memberStockService.getMemberStocks(memberId);
        List<ResponseMemberStock> result = new ArrayList<>();

        memberStockList.forEach(s -> {
            Optional<Stock> stock = stockService.getStock(s.getStock().getId());

            ResponseMemberStock responseMemberStock = mapper.map(s, ResponseMemberStock.class);
            responseMemberStock.setStockId(stock.get().getId());
            responseMemberStock.setStockName(stock.get().getStockName());
            responseMemberStock.setStockCode(stock.get().getStockCode());
            result.add(responseMemberStock);
        });
        return Api.OK(result);
    }
}
