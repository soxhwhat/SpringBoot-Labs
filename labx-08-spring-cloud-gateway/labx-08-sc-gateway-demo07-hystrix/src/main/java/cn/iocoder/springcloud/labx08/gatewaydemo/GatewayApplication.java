package cn.iocoder.springcloud.labx08.gatewaydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        /**
         * Hystrix 库，是 Netflix 开源的一个针对分布式系统的延迟和容错库。
         *
         * Hystrix 供分布式系统使用，提供延迟和容错功能，隔离远程系统、访问和第三方程序库的访问点，防止级联失败，保证复杂的分布系统在面临不可避免的失败时，仍能有其弹性。
         */
        SpringApplication.run(GatewayApplication.class, args);
    }

}
