package cn.iocoder.springboot.lab48.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Spring Boot 通过使用两个类加载器来提供了重启技术。
 *
 * 不改变的类（例如，第三方 jar）被加载到 base 类加载器中。
 * 经常处于开发状态的类被加载到 restart 类加载器中。
 * 当应用重启时，restart 类加载器将被丢弃，并重新创建一个新的。这种方式意味着应用重启比冷启动要快得多，因为省去 base 类加载器的处理步骤，并且可以直接使用。
 */
@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}
