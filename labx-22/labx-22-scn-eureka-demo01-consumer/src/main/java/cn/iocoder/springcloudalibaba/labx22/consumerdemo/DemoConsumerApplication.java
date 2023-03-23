package cn.iocoder.springcloudalibaba.labx22.consumerdemo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@SpringBootApplication
//DiscoveryClient 对象，服务发现客户端，上文我们已经介绍过。这里我们注入的不是 Eureka 提供的 EurekaDiscoveryClient，保证通用性。未来如果我们不使用 Eureka 作为注册中心，而是使用 Nacos 或则 Zookeeper 时，则无需改动这里的代码。
// @EnableDiscoveryClient
public class DemoConsumerApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoConsumerApplication.class, args);
    }

    @Configuration
    public class RestTemplateConfiguration {

        @Bean
        public RestTemplate restTemplate() {
            return new RestTemplate();
        }

    }

    @RestController
    static class TestController {

        @Autowired
        private DiscoveryClient discoveryClient;
        @Autowired
        private RestTemplate restTemplate;
        @Autowired
        //loadBalancerClient 属性，LoadBalancerClient 对象，负载均衡客户端。稍后我们会使用它，从 Eureka 获取的服务 demo-provider 的实例列表中，选择一个进行 HTTP 调用。
        private LoadBalancerClient loadBalancerClient;

        @GetMapping("/hello")
        public String hello(String name) {
            // 获得服务 `demo-provider` 的一个实例
            ServiceInstance instance;
            if (true) {
                // 获取服务 `demo-provider` 对应的实例列表
                List<ServiceInstance> instances = discoveryClient.getInstances("demo-provider");
                // 选择第一个
                instance = instances.size() > 0 ? instances.get(0) : null;
            } else {
                instance = loadBalancerClient.choose("demo-provider");
            }
            // 发起调用
            if (instance == null) {
                throw new IllegalStateException("获取不到实例");
            }
            String targetUrl = instance.getUri() + "/echo?name=" + name;
            String response = restTemplate.getForObject(targetUrl, String.class);
            // 返回结果
            return "consumer:" + response;
        }

    }

}
