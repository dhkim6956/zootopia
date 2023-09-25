package com.ssafy.classserver.service;

import com.ssafy.classserver.dto.ProductDto;
import com.ssafy.classserver.jpa.BankEntity;
import com.ssafy.classserver.jpa.BankRepository;
import com.ssafy.classserver.jpa.ProductRepository;
import com.ssafy.classserver.jpa.SavingProductsEntity;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Data
@Slf4j
public class BankServiceImpl implements BankService{

    BankRepository bankRepository;
    ProductRepository productRepository;

    @Autowired
    public BankServiceImpl(BankRepository bankRepository, ProductRepository productRepository) {
        this.bankRepository = bankRepository;
        this.productRepository = productRepository;
    }

    @Override
    public Iterable<BankEntity> getAllBanks() {
        return bankRepository.findAll();
    }

    @Override
    public BankEntity getClassBanks(UUID classId) {
        return bankRepository.findByClassId(classId);
    }

    @Override
    public ProductDto createProduct(ProductDto product) {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        SavingProductsEntity productEntity = mapper.map(product, SavingProductsEntity.class);

        productRepository.save(productEntity);

        return new ModelMapper().map(productEntity, ProductDto.class);

    }
}
