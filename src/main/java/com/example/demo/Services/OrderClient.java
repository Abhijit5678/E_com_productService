package com.example.demo.Services;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.demo.Bean.OrderBean;
import com.example.demo.Utils.Response;

@FeignClient(name = "order-services")
public interface OrderClient {

    @GetMapping("/orders/{orderId}")
    OrderBean getProductById(@PathVariable int orderId);
    
}

