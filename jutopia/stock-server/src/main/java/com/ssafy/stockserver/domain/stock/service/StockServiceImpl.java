package com.ssafy.stockserver.domain.stock.service;

import com.ssafy.stockserver.domain.client.NewsServerClient;
import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.stock.repository.StockRepository;
import com.ssafy.stockserver.domain.stock.vo.request.RequestStock;
import com.ssafy.stockserver.domain.stock.vo.response.ResponseStock;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.web.client.RestTemplate;

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
    public Iterable<Stock> getAllStocks() {
        Iterable<Stock> stock = stockRepository.findAll();
        List<ResponseStock> resList = new ArrayList<>();

        String res = newsServerClient.getStocks();
        System.out.println(res);

//        ResponseEntity<List<ResponseOrder>> ordersListResponse =
//        restTemplate.exchange(newsURL, HttpMethod.GET, null,
//            new ParameterizedTypeReference<List<ResponseOrder>>() {
//        });
//        // 받아온 값의 body로 orders 가져오기
//        List<ResponseOrder> ordersList = ordersListResponse.getBody();
//        userDto.setOrders(ordersList);

//        stock.forEach(s -> {
//            ResponseStock res = mapper.map(s, ResponseStock.class);
//
//
//        });
//


        return stockRepository.findAll();
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
