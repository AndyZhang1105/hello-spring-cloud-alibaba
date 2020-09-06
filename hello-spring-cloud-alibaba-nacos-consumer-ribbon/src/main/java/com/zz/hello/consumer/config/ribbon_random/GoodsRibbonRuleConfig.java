package com.zz.hello.consumer.config.ribbon_random;

import com.netflix.loadbalancer.IRule;
import com.netflix.loadbalancer.RandomRule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//@Configuration
public class GoodsRibbonRuleConfig {

//    @Bean
    public IRule ribbonRulr() {
        return new RandomRule();
    }

}
