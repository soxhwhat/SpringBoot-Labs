package cn.iocoder.springcloud.labx08.gatewaydemo;

import com.alibaba.cloud.sentinel.gateway.ConfigConstants;
import com.alibaba.csp.sentinel.config.SentinelConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        //Sentinel 是阿里中间件团队开源的，面向分布式服务架构的轻量级流量控制产品，主要以流量为切入点，从流量控制、熔断降级、系统负载保护等多个维度来帮助用户保护服务的稳定性。
        System.setProperty(SentinelConfig.APP_TYPE, ConfigConstants.APP_TYPE_SCG_GATEWAY); // 【重点】设置应用类型为 Spring Cloud Gateway
        SpringApplication.run(GatewayApplication.class, args);
    }

}
