package com.ssafy.stockserver.common.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Stock Server",
                description = "Stock Server api명세",
                version = "v3"))
@RequiredArgsConstructor
@Configuration
// 스웨거 페이지 만들기
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/stock-server/api/**"};

        return GroupedOpenApi.builder()
                .group("class server API v3")
                .pathsToMatch(paths)
                .build();
    }
}