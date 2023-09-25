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
@RequestMapping("/class-server/api/banks")
public class ProductsController {

    Environment env;
    BankService bankService;

    public ProductsController(Environment env, BankService bankService) {
        this.env = env;
        this.bankService = bankService;
    }

    @PostMapping("/{bankId}")
    public Api<ResponseProduct> createProduct(@RequestBody RequestProduct product
                                                        , @PathVariable UUID bankId) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        ProductDto productDto = mapper.map(product, ProductDto.class);
        productDto.setBankId(bankId);

        bankService.createProduct(productDto);
        ResponseProduct responseProduct = mapper.map(productDto, ResponseProduct.class);

        return Api.CREATED(responseProduct);
    }
}
