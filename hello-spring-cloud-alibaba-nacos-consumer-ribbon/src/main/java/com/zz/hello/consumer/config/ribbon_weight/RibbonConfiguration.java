package com.zz.hello.consumer.config.ribbon_weight;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RibbonConfiguration {

//    @Bean
    public IRule ribbonRule() {
        return new NacosWeightedRule();
    }
}