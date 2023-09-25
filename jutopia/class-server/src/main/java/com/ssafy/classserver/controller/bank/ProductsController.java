package com.ssafy.classserver.controller.bank;

import com.ssafy.classserver.dto.ProductDto;
import com.ssafy.classserver.service.BankService;
import com.ssafy.classserver.vo.request.RequestProduct;
import com.ssafy.classserver.vo.response.ResponseProduct;
import com.ssafy.common.api.Api;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/class-server/api/bank")
public class ProductsController {

    Environment env;
    BankService bankService;
    ModelMapper mapper;

    public ProductsController(Environment env, BankService bankService) {
        this.env = env;
        this.bankService = bankService;
        this.mapper = new ModelMapper();
        this.mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
    }

    // 반 적금 상품 생성
    @PostMapping("/{classId}")
    public Api<ResponseProduct> createProduct(@RequestBody RequestProduct product
                                                        , @PathVariable UUID classId) {

        ProductDto productDto = mapper.map(product, ProductDto.class);
        productDto.setClassId(classId);

        bankService.createProduct(productDto);
        ResponseProduct responseProduct = mapper.map(productDto, ResponseProduct.class);

        return Api.CREATED(responseProduct);
    }
}
