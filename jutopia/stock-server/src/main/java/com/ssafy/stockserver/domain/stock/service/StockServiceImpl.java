package com.ssafy.stockserver.domain.stock.service;

import com.ssafy.common.api.Api;
import com.ssafy.stockserver.domain.client.NewsServerClient;
import com.ssafy.stockserver.domain.client.ResponseFeignStock;
import com.ssafy.stockserver.domain.memberStock.service.MemberStockService;
import com.ssafy.stockserver.domain.memberStock.service.MemberStockServiceImpl;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.stock.repository.StockRepository;
import com.ssafy.stockserver.domain.stock.vo.request.RequestStock;
import com.ssafy.stockserver.domain.stock.vo.response.ResponseStock;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;

@Service
@Slf4j
public class StockServiceImpl implements StockService{
    ModelMapper mapper;
    StockRepository stockRepository;
    NewsServerClient newsServerClient;
    MemberStockService memberStockService;
    @Autowired
    public StockServiceImpl(StockRepository stockRepository, NewsServerClient newsServerClient, MemberStockService memberStockService) {
        this.stockRepository = stockRepository;
        this.newsServerClient = newsServerClient;
        this.memberStockService = memberStockService;
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public List<ResponseStock> getAllStocks(UUID userId) {
        Iterable<Stock> stock = stockRepository.findAll();
        List<ResponseStock> resList = new ArrayList<>();
        var MyStocks = memberStockService.getMemberStocks(userId);
        Set<String> ownedStockCode = new HashSet<>();
        MyStocks.forEach(s -> ownedStockCode.add(s.getStock().getStockCode()));

        // news-server 로부터 실시간 주식 데이터 가져와 가공
        List<ResponseFeignStock> res = newsServerClient.getStocks();
        res.stream().forEach(r -> {
            r.setNowMoney(r.getNowMoney().replaceAll(",",""));
            r.setPrevMoney(r.getPrevMoney().replaceAll(",",""));
        });

        // response 객체 가공
        stock.forEach(s -> {
            ResponseStock newStock = mapper.map(s, ResponseStock.class);

            Optional<ResponseFeignStock> tmp = res.stream().filter(e -> e.getStockName().equals(s.getStockName())).findFirst();
            BigDecimal nowMoney = new BigDecimal(tmp.get().getNowMoney());
            BigDecimal prevMoney = new BigDecimal(tmp.get().getPrevMoney());

            newStock.setNowMoney(nowMoney);
            newStock.setPrevMoney(prevMoney);
            newStock.setChangeMoney(nowMoney.subtract(prevMoney));
            newStock.setChangeRate(nowMoney.subtract(prevMoney).divide(prevMoney,3, RoundingMode.HALF_UP)
                                    .multiply(new BigDecimal("100")).doubleValue());
            if(nowMoney.compareTo(prevMoney) > 0) newStock.setType(1);
            else if (nowMoney.compareTo(prevMoney) < 0) newStock.setType(-1);
            else newStock.setType(0);

            if (ownedStockCode.contains(s.getStockCode())) {
                newStock.setIsOwnedByUser(true);
            } else {
                newStock.setIsOwnedByUser(false);
            }

            resList.add(newStock);
        });
        return resList;
    }

    @Override
    public ResponseStock createStock(RequestStock stock) {
        Stock returnStock = stockRepository.save(mapper.map(stock, Stock.class));
        return mapper.map(returnStock, ResponseStock.class);
    }

    @Override
    public ResponseStock getStock(UUID stockId) {
        Optional<Stock> stock = stockRepository.findById(stockId);

        if (!stock.isPresent()) return null;

        // Optional이 값으로 채워져 있다면 매핑을 수행
        ResponseStock responseStock = mapper.map(stock.get(), ResponseStock.class);

        ResponseFeignStock feignStock = newsServerClient.getOneStock(responseStock.getStockCode());

        BigDecimal nowMoney = new BigDecimal(feignStock.getNowMoney().replaceAll(",", ""));
        BigDecimal prevMoney = new BigDecimal(feignStock.getPrevMoney().replaceAll(",", ""));

        responseStock.setNowMoney(nowMoney);
        responseStock.setPrevMoney(prevMoney);
        responseStock.setChangeMoney(nowMoney.subtract(prevMoney));
        responseStock.setChangeRate(nowMoney.subtract(prevMoney).divide(prevMoney, 3, RoundingMode.HALF_UP)
                .multiply(new BigDecimal("100")).doubleValue());
        if (nowMoney.compareTo(prevMoney) > 0) responseStock.setType(1);
        else if (nowMoney.compareTo(prevMoney) < 0) responseStock.setType(-1);
        else responseStock.setType(0);

        return responseStock;
    }

    @Override
    public Optional<Stock> getOneStock(UUID stockId) {
        return stockRepository.findById(stockId);
    }
}
