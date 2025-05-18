package com.seafoodveggies.order_service.client;

import com.seafoodveggies.order_service.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@FeignClient(name = "user-service", url = "${user-service.base-url}")
public interface UserFeignClient {
    @GetMapping("/api/user/{userId}")
    User getUserById( @PathVariable Long userId);
}
