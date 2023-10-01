package com.ssafy.rentserver.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.common.objectmapper.ObjectMapperConfig;
import io.swagger.v3.core.jackson.ModelResolver;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Rent Server",
                description = "Rent Server api명세",
                version = "v3"))
@RequiredArgsConstructor
@Configuration
// 스웨거 페이지 만들기
public class SwaggerConfig {

    private final ObjectMapperConfig objectMapperConfig;
    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"api/**"};

        return GroupedOpenApi.builder()
                .group("class server API v3")
                .pathsToMatch(paths)
                .build();
    }
    @Bean
    public ModelResolver modelResolver(ObjectMapper objectMapper){
        return new ModelResolver(objectMapperConfig.objectMapper());
    }
}