package cn.iocoder.springboot.lab43.propertydemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.config.ConfigFileApplicationListener;
import org.springframework.stereotype.Component;

import javax.swing.*;

@SpringBootApplication
public class Application {

    /**
     * 设置需要读取的配置文件的名字。
     * 基于 {@link org.springframework.boot.context.config.ConfigFileApplicationListener#CONFIG_NAME_PROPERTY} 实现。
     */
    private static final String CONFIG_NAME_VALUE = "application,rpc";

    public static void main(String[] args) {
        // 设置环境变量 因为 spring.config.name 配置项，必须在读取配置文件之前完成设置，所以我们在 <X> 处，通过环境变量来设置。
        // 我们给每个项目创建了一个独立的配置文件名，同时设置 spring.config.name 配置项为 application,demo,rpc。这样，
        // Spring Boot 就会读取这三个配置文件。并且，它和「6. 多环境配置」是可以共存使用的。
        System.setProperty(ConfigFileApplicationListener.CONFIG_NAME_PROPERTY, CONFIG_NAME_VALUE);

        // 启动 Spring Boot 应用
        SpringApplication.run(Application.class, args);
    }

    @Component
    public class ValueCommandLineRunner implements CommandLineRunner {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        @Value("${application-test}")
        private String applicationTest;

        @Value("${rpc-test}")
        private String rpcTest;

        @Override
        public void run(String... args) {
            logger.info("applicationTest:" + applicationTest);
            logger.info("rpcTest:" + rpcTest);
        }

    }

}
