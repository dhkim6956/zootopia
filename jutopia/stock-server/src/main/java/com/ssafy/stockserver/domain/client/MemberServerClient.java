package com.ssafy.stockserver.domain.client;

import com.ssafy.stockserver.common.api.ApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

// 호출할 서비스명 선언
@FeignClient(name="member-server")
public interface MemberServerClient {

    @GetMapping("/member-server/api/student/{userId}")
    ApiResponse getStudent(@PathVariable("userId") UUID userId);
}
