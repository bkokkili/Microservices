package com.seafoodveggies.order_service.client;

import com.seafoodveggies.order_service.model.Product;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "product-service", url = "${product-service.base-url}")
public interface ProductFeignClient {

    @GetMapping("/api/products/{id}")
    Product getProductById(@PathVariable  Long id);


}
