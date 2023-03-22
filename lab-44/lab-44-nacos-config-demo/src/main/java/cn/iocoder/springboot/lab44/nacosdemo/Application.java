package cn.iocoder.springboot.lab44.nacosdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
//这里，我们注释了一段 @NacosConfigurationProperties 注解的代码，该注解在功能上是对标 @ConfigurationProperties 注解，用于将 Nacos 配置注入 POJO 配置类中。为什么我们这里注释掉了呢？因为我们在「2.2 配置文件」中，设置了 nacos.config.bootstrap.enable=true，Spring Boot 应用在启动时，预加载了来自 Nacos 配置，所以可以直接使用 @ConfigurationProperties 注解即可。这样的好处，是可以更加通用，而无需和 Nacos 有耦合与依赖。
// @NacosPropertySource(dataId = "example", type = ConfigType.YAML)
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Component
    public class OrderPropertiesCommandLineRunner implements CommandLineRunner {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        @Autowired
        private OrderProperties orderProperties;

        @Override
        public void run(String... args) {
            logger.info("payTimeoutSeconds:" + orderProperties.getPayTimeoutSeconds());
            logger.info("createFrequencySeconds:" + orderProperties.getCreateFrequencySeconds());
        }

    }

    @Component
    public class ValueCommandLineRunner implements CommandLineRunner {

        private final Logger logger = LoggerFactory.getLogger(getClass());

//        我们注释了一段 @NacosValue 注解的代码，该注解在功能上是对标 @Value 注解，用于将 Nacos 配置注入属性种。为什么我们这里注释掉了呢？原因同 @NacosConfigurationProperties 注解。
//        @NacosValue(value = "${order.pay-timeout-seconds}")
        @Value(value = "${order.pay-timeout-seconds}")
        private Integer payTimeoutSeconds;

//        @NacosValue(value = "${order.create-frequency-seconds}")
        @Value(value = "${order.create-frequency-seconds}")
        private Integer createFrequencySeconds;

        @Override
        public void run(String... args) {
            logger.info("payTimeoutSeconds:" + payTimeoutSeconds);
            logger.info("createFrequencySeconds:" + createFrequencySeconds);
        }
    }

}
