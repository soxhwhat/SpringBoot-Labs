package cn.iocoder.springcloud.labx08.gatewaydemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
public class GatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(GatewayApplication.class, args);
//        使用浏览器，访问 http://127.0.0.1:8888/blog，成功转发到目标 URI http://www.iocoder.cn，如下图所示：
//        使用浏览器，访问 http://127.0.0.1:8888/oschina，成功转发到目标 URI http://www.oschina.net
    }

    @RestController
    @RequestMapping("/test")
    public class TestController {

        @RequestMapping("/hello")
        public String hello() {
            return "hello";
        }

    }

}
