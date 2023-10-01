package com.ssafy.memberserver.domain.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;


import java.util.Arrays;
import java.util.List;

//@RequiredArgsConstructor
@Configuration
////@EnableWebSecurity
public class SecurityConfig {
//    private final List<String> CORS_ALLOW_LIST = Arrays.asList();
//    private final List<String> CORS_ALLOW_METHOD = Arrays.asList();
//    private final List<String> CORS_ALLOW_HEADER = Arrays.asList();
//    private final List<String> ALLOW_URL = Arrays.asList();
//
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

//    private final JwtAuthenticationFilter jwtAuthenticationFilter;
//    private final AuthenticationEntryPoint entryPoint;
//    @Bean
//    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
////        String ALLOW_URL[] = {"/,/**,/sign-up", "/sign-in","/swagger-ui/**", "/api/swagger-ui/**", "/api/**", "/v3/api-docs/**"};
//        return http
//                .cors(cors -> cors.configurationSource(corsConfigurationSource()))
////                .formLogin().disable()
////                .httpBasic().disable()
//                .csrf().disable()
////            .cors().disable()
//                .headers(headers -> headers.frameOptions().sameOrigin())
//                .authorizeHttpRequests(request -> request.requestMatchers("/**").permitAll()
//                        .anyRequest().authenticated())
////                .sessionManagement(httpSecuritySessionManagementConfigurer -> httpSecuritySessionManagementConfigurer.sessionCreationPolicy(
////                        SessionCreationPolicy.STATELESS))
////                .addFilterBefore(jwtAuthenticationFilter, BasicAuthenticationFilter.class)
////                .exceptionHandling(handler -> handler.authenticationEntryPoint(entryPoint))
//                .build();
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
    //,,,,,,,,,,,,,,,,,,,,,,,,,ㅎㅎㅎ
}
