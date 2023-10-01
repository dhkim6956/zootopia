package com.ssafy.stockserver.domain.stock.service;

import com.ssafy.stockserver.domain.stock.entity.Stock;
import com.ssafy.stockserver.domain.stock.repository.StockRepository;
import com.ssafy.stockserver.domain.stock.vo.request.RequestStock;
import com.ssafy.stockserver.domain.stock.vo.response.ResponseStock;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
public class StockServiceImpl implements StockService{
    ModelMapper mapper;
    StockRepository stockRepository;

    @Autowired
    public StockServiceImpl(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    @Override
    public Iterable<Stock> getAllStocks() {
        Iterable<Stock> stock = stockRepository.findAll();


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
