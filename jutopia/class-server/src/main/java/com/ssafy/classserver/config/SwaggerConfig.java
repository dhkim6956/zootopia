package com.ssafy.classserver.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@OpenAPIDefinition(
        info = @Info(title = "Class Server",
                description = "Class Server api명세",
                version = "v1"))
@RequiredArgsConstructor
@Configuration
// 스웨거 페이지 만들기
public class SwaggerConfig {
    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/class-server/api/**"};

        return GroupedOpenApi.builder()
                .group("class server API v1")
                .pathsToMatch(paths)
                .build();
    }
}