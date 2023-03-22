package cn.iocoder.springboot.lab44.nacosdemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.Environment;

@SpringBootApplication
// @NacosPropertySource(dataId = "example", type = ConfigType.YAML)
public class Application {

    public static void main(String[] args) {
        // 启动 Spring Boot 应用
        ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);

        // 查看 Environment
        Environment environment = context.getEnvironment();
        /**
         * 每一个 Nacos 配置集，对应一个 PropertySource 对象，并且 nacos.config 配置项下的优先级高于 nacos.config.ext-config。
         * 所有 Nacos 配置集的 PropertySource 对象，排在 application.yaml 配置文件的 PropertySource 对象后面，也就是优先级最低
         */
        System.out.println(environment);
    }

}
