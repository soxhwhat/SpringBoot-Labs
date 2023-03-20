package cn.iocoder.springboot.lab23.springmvc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
//添加 @ServletComponentScan 注解，开启对 @WebServlet、@WebFilter、@WebListener 注解的扫描。不过要注意，当且仅当使用内嵌的 Web Server 才会生效。
@ServletComponentScan
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

}
