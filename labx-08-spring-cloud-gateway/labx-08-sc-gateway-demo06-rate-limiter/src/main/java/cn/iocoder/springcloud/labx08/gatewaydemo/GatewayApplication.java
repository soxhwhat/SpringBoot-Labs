package cn.iocoder.springcloud.labx08.gatewaydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {
    /**
     * Gateway 内置 RequestRateLimiterGatewayFilterFactory 提供请求限流的功能。该 Filter 是基于 Token Bucket Algorithm（令牌桶算法）实现的限流，同时搭配上 Redis 实现分布式限流。
     *
     *
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
    }

}
