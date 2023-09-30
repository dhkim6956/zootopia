package com.ssafy.stockserver.domain.trading.controller;

import com.ssafy.common.api.Api;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.stock.service.StockService;
import com.ssafy.stockserver.domain.stock.vo.response.ResponseStock;
import com.ssafy.stockserver.domain.trading.entity.Trading;
import com.ssafy.stockserver.domain.trading.kafka.KafkaProducer;
import com.ssafy.stockserver.domain.trading.service.TradingService;
import com.ssafy.stockserver.domain.trading.vo.request.RequestTrade;
import com.ssafy.stockserver.domain.trading.vo.response.ResponseTrade;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/stock-server/api/trade")
public class TradingController {

    TradingService tradingService;
    StockService stockService;
    KafkaProducer kafkaProducer;
    ModelMapper mapper;

    public TradingController(TradingService tradingService, StockService stockService, KafkaProducer kafkaProducer) {
        this.tradingService = tradingService;
        this.stockService = stockService;
        this.kafkaProducer = kafkaProducer;
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @PostMapping("/")
    public Api<ResponseTrade> createTrade(@RequestBody RequestTrade requestTrade) {
        Trading trade = mapper.map(requestTrade, Trading.class);
        Optional<Stock> stock = stockService.getStock(requestTrade.getStockId());
        if (stock.isPresent()) {
            trade.setStock(stock.get());
            trade.setTotalPrice(trade.getPrice().multiply(BigDecimal.valueOf(trade.getVolume())));
            System.out.println(trade);
            trade = tradingService.save(trade);

            kafkaProducer.send("member-point", requestTrade);

            ResponseTrade result = mapper.map(trade, ResponseTrade.class);
            result.setStockName(stock.get().getStockName());
            result.setStockCode(stock.get().getStockCode());
            return Api.CREATED(result);
        } else {
            // Optional이 비어있는 경우에 대한 처리
            return Api.NOT_FOUND(null); // 예: 404 Not Found
        }
    }
}
