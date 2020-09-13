package com.zz.hello.consumer.config.ribbon_random;

import org.springframework.cloud.netflix.ribbon.RibbonClients;
import org.springframework.context.annotation.Configuration;

@Configuration
@RibbonClients(defaultConfiguration = GoodsRibbonRuleConfig.class)//Ribbon负载均衡全局粒度配置（所有服务都按照这个配置）
public class RibbonConfig {
}