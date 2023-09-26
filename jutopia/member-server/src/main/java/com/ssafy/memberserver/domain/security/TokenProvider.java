package com.ssafy.memberserver.domain.security;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.memberserver.common.config.YamlLoadFactory;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;

import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Base64;
import java.util.Date;
import java.util.Map;


@Service
@PropertySource(value = {"token.yaml"}, factory = YamlLoadFactory.class)
public class TokenProvider {
    private final ObjectMapper objectMapper = new ObjectMapper();
    @Value("${secret-key}")
    String secretKey;
    @Value("${expiration-minutes}")
    long expirationMinutes;
//    @Value("${secret-key}")
//    String secretKey;
//    @Value("${secret-key}")
//    String secretKey;

    //TODO: subject를 유니크한 값으로 넣기
    public String createAccessToken(String memberSpecification){
        return Jwts.builder()
                        .setIssuer("test")
                        .setIssuedAt(new Date())
                        .setSubject("test")
                        .setExpiration(Date.from(Instant.now().plus(expirationMinutes, ChronoUnit.HOURS)))
                        .signWith(new SecretKeySpec(secretKey.getBytes(),SignatureAlgorithm.HS512.getJcaName()))
                .compact();
    }
//    public String createRefreshToken(){
//
//    }
    //디코더 후 "sub"값만 가져 오는 함수
    //split[0]은 header, split[1] = payload
    public String decodeJwtPayloadSubject(String oldAccessToken) throws JsonProcessingException {
        return objectMapper.readValue(
                new String(Base64.getDecoder().decode(oldAccessToken.split("\\.")[1]), StandardCharsets.UTF_8),
                Map.class
        ).get("sub").toString();

    }
}
