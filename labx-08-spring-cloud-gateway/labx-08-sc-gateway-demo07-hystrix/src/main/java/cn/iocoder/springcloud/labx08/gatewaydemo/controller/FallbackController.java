package cn.iocoder.springcloud.labx08.gatewaydemo.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;

@RestController
public class FallbackController {

    private Logger logger = LoggerFactory.getLogger(FallbackController.class);

    /**
     * /ask@GPT4Chat_bot ServerWebExchange是什么？如何使用
     *
     * ServerWebExchange是Spring WebFlux框架中的一个接口，用于表示服务器与客户端之间进行的请求-响应交换。
     *
     * 使用ServerWebExchange，可以实现以下操作：
     *
     * 1. 获取请求信息，例如请求地址、请求方法、请求头信息等；
     * 2. 读取请求体数据；
     * 3. 设置响应信息，例如设置响应状态码、响应头信息、响应体数据等；
     * 4. 发送响应数据给客户端。
     *
     * 使用ServerWebExchange的基本步骤如下：
     *
     * 1. 创建一个处理HTTP请求的Handler方法；
     * 2. 在Handler方法中获取ServerWebExchange对象；
     * 3. 使用ServerWebExchange对象进行相应的操作；
     *
     * 例如，在一个控制器中处理HTTP GET请求的代码如下：
     *
     * @GetMapping("/hello")
     * public Mono<String> sayHello(ServerWebExchange exchange) {
     *     String name = exchange.getRequest().getQueryParams().getFirst("name");
     *     if (name == null) {
     *         name = "World";
     *     }
     *     String message = "Hello, " + name + "!";
     *     return Mono.just(message);
     * }
     *
     *
     * 在上述代码中，使用exchange.getRequest()方法获取请求信息，然后根据请求参数获取名称，并创建响应字符串。最后，使用Mono.just(message)方法创建一个Mono对象，表示响应数据。
     * @param exchange
     * @return
     */
    @GetMapping("/fallback")
    public String fallback(ServerWebExchange exchange) {
//        URI requestUrl = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_REQUEST_URL_ATTR);
        Throwable executionException = exchange.getAttribute(ServerWebExchangeUtils.HYSTRIX_EXECUTION_EXCEPTION_ATTR);
        logger.error("[fallback][发生异常]", executionException);

        return "服务降级..." + executionException.getMessage();
    }

}
