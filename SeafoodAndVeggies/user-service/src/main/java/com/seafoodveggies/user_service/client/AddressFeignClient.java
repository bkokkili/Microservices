package com.seafoodveggies.user_service.client;

import com.seafoodveggies.user_service.model.Address;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@FeignClient(name = "address-service", url = "${address-service.base-url}")
public interface AddressFeignClient {
    @GetMapping("/api/address/user/{userId}")
    List<Address> getAddressByUserId(@PathVariable Long userId);
}
