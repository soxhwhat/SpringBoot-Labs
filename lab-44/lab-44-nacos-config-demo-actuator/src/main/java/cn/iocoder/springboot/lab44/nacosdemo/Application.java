package cn.iocoder.springboot.lab44.nacosdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
// @NacosPropertySource(dataId = "example", type = ConfigType.YAML)
//而 Nacos 有个 nacos-config-spring-boot-actuator 子项目，提供了 Nacos 作为 Spring Boot 配置中心时的监控端点。
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
