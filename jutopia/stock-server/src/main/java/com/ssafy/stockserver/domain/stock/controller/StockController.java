package com.ssafy.stockserver.domain.stock.controller;

import com.ssafy.common.api.Api;
import com.ssafy.stockserver.domain.memberStock.service.MemberStockService;
import com.ssafy.stockserver.domain.memberStock.entity.MemberStock;
import com.ssafy.stockserver.domain.memberStock.vo.response.ResponseMemberStock;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.stock.service.StockService;
import com.ssafy.stockserver.domain.stock.vo.request.RequestStock;
import com.ssafy.stockserver.domain.stock.vo.response.ResponseStock;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/stock-server/api/stock")
@Tag(name = "Stock", description = "주식 종목 조회")
public class StockController {

    StockService stockService;
    MemberStockService memberStockService;
    ModelMapper mapper;

    @Autowired
    public StockController(StockService stockService, MemberStockService memberStockService) {
        this.stockService = stockService;
        this.memberStockService = memberStockService;
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @GetMapping("/")
    public Api<List<ResponseStock>> getAllStocks() {
        Iterable<Stock> stockList = stockService.getAllStocks();
        List<ResponseStock> result = new ArrayList<>();

        stockList.forEach(s -> result.add(mapper.map(s, ResponseStock.class)));
        return Api.OK(result);
    }

    @PostMapping("/")
    public Api<ResponseStock> createStock(@RequestBody RequestStock stock) {
        return Api.OK(stockService.createStock(stock));
    }

    @GetMapping("/{stockId}")
    public Api<ResponseStock> getStock(@PathVariable UUID stockId) {
        Optional<Stock> stock = stockService.getStock(stockId);
        if (stock.isPresent()) {
            // Optional이 값으로 채워져 있다면 매핑을 수행
            ResponseStock responseStock = mapper.map(stock.get(), ResponseStock.class);
            return Api.OK(responseStock);
        } else {
            // Optional이 비어있는 경우에 대한 처리
            return Api.NOT_FOUND(null); // 예: 404 Not Found
        }
    }
}
