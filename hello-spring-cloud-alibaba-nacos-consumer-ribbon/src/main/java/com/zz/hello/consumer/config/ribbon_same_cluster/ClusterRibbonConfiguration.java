package com.zz.hello.consumer.config.ribbon_same_cluster;

import com.netflix.loadbalancer.IRule;
import org.springframework.context.annotation.Bean;

//@Configuration
public class ClusterRibbonConfiguration {

    @Bean
    public IRule ribbonRule() {
        return new NacosRule();
    }
}