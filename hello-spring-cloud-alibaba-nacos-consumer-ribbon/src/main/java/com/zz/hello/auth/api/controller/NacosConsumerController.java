package com.zz.hello.consumer.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class NacosConsumerController {

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping(value = "/test/app/name")
    public String test() {
        String url = String.format("http://%s/test/%s", "nacos-provider", "ribbon");
        return restTemplate.getForObject(url, String.class);
    }
}
