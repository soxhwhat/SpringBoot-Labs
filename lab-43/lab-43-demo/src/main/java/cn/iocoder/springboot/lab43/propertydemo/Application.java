package cn.iocoder.springboot.lab43.propertydemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Component;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    //在 OrderPropertiesCommandLineRunner 类中，我们测试了使用 @ConfigurationProperties 注解的 OrderProperties 配置类，读取 order 配置项的效果。
    @Component
    public class OrderPropertiesCommandLineRunner implements CommandLineRunner {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        @Autowired
        private OrderProperties orderProperties;

        @Override
        public void run(String... args) {
            logger.info("payTimeoutSeconds:" + orderProperties.getPayTimeoutSeconds());
            logger.info("createFrequencySeconds:" + orderProperties.getCreateFrequencySeconds());
            //配置随机值
            logger.info("desc:" + orderProperties.getDesc());
        }

    }

    // ValueCommandLineRunner 类中，我们测试了使用 @Value 注解，读取 order 配置项的效果。
    @Component
    public class ValueCommandLineRunner implements CommandLineRunner {

        private final Logger logger = LoggerFactory.getLogger(getClass());

        @Value("${order.pay-timeout-seconds}")
        private Integer payTimeoutSeconds;

        @Value("${order.create-frequency-seconds}")
        private Integer createFrequencySeconds;

//        @Value("${order.desc}")
//        private String desc;

        @Override
        public void run(String... args) {
            logger.info("payTimeoutSeconds:" + payTimeoutSeconds);
            logger.info("createFrequencySeconds:" + createFrequencySeconds);
        }

    }

}
