package com.ssafy.stockserver.domain.stock.service;

import com.ssafy.stockserver.domain.client.NewsServerClient;
import com.ssafy.stockserver.domain.client.ResponseFeignStock;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class StockServiceImpl implements StockService{
    ModelMapper mapper;
    StockRepository stockRepository;
    NewsServerClient newsServerClient;
    @Autowired
    public StockServiceImpl(StockRepository stockRepository, NewsServerClient newsServerClient) {
        this.stockRepository = stockRepository;
        this.newsServerClient = newsServerClient;
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public List<ResponseStock> getAllStocks() {
        Iterable<Stock> stock = stockRepository.findAll();
        List<ResponseStock> resList = new ArrayList<>();

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
    public Optional<Stock> getStock(UUID stockId) {
        return stockRepository.findById(stockId);
    }
}
