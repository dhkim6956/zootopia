package com.ssafy.memberserver.domain.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    private final List<String> CORS_ALLOW_LIST = Arrays.asList();
    private final List<String> CORS_ALLOW_METHOD = Arrays.asList();
    private final List<String> CORS_ALLOW_HEADER = Arrays.asList();
    private final List<String> ALLOW_URL = Arrays.asList();

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
//        return http
//                .cors().disable()
//                .csrf().disable()
//                .authorizeHttpRequests(request -> request
//                        .requestMatchers("/**").permitAll()
//                )
//                .build();
//
//    }

//        @Bean
//        CorsConfigurationSource corsConfigurationSource(){
//            CorsConfiguration corsConfiguration = new CorsConfiguration();
//
//            corsConfiguration.setAllowedOrigins(CORS_ALLOW_LIST);
//            corsConfiguration.setAllowedMethods(CORS_ALLOW_METHOD);
//            corsConfiguration.setAllowedHeaders(CORS_ALLOW_HEADER);
//            corsConfiguration.setAllowCredentials(true);
//            UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//            source.registerCorsConfiguration("/**",corsConfiguration);
//            return source;
//    }
}
