package com.zz.hello.consumer.ribbon.config.ribbon_same_cluster;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.ribbon.ExtendBalancer;
import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.netflix.client.config.IClientConfig;
import com.netflix.loadbalancer.AbstractLoadBalancerRule;
import com.netflix.loadbalancer.DynamicServerListLoadBalancer;
import com.netflix.loadbalancer.Server;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 支持优先调用同集群实例的ribbon负载均衡规则.
 *
 * @author itmuch.com
 */
@Slf4j
public class NacosRule extends AbstractLoadBalancerRule {

    @Autowired
    private NacosDiscoveryProperties nacosDiscoveryProperties;

    @Override
    public Server choose(Object key) {
        try {
            String clusterName = this.nacosDiscoveryProperties.getClusterName();
            DynamicServerListLoadBalancer loadBalancer = (DynamicServerListLoadBalancer) getLoadBalancer();
            String name = loadBalancer.getName();

            NamingService namingService = this.nacosDiscoveryProperties.namingServiceInstance();
            // 1. 找到指定服务的所有实例 A
            List<Instance> instances = namingService.selectInstances(name, true);
            if (CollectionUtils.isEmpty(instances)) {
                return null;
            }

            List<Instance> instancesToChoose = instances;
            if (StringUtils.isNotBlank(clusterName)) {
                // 2. 过滤出相同集群下的所有实例 B
                List<Instance> sameClusterInstances = instances.stream()
                        .filter(instance -> Objects.equals(clusterName, instance.getClusterName()))
                        .collect(Collectors.toList());
                // 3. 如果B为空，就用A
                if (!CollectionUtils.isEmpty(sameClusterInstances)) {
                    instancesToChoose = sameClusterInstances;
                } else {
                    log.warn("发生跨集群的调用，name = {}, clusterName = {}, instance = {}", name, clusterName, instances);
                }
            }
            // 4. 基于权重的负载均衡算法，返回一个实例
            Instance instance = ExtendBalancer.getHostByRandomWeight2(instancesToChoose);

            return new NacosServer(instance);
        } catch (Exception e) {
            log.warn("NacosRule发生异常", e);
            return null;
        }
    }

    @Override
    public void initWithNiwsConfig(IClientConfig iClientConfig) {
    }
}