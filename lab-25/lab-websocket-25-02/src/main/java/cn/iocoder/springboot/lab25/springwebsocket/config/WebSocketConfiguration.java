package cn.iocoder.springboot.lab25.springwebsocket.config;

import cn.iocoder.springboot.lab25.springwebsocket.websocket.DemoWebSocketHandler;
import cn.iocoder.springboot.lab25.springwebsocket.websocket.DemoWebSocketShakeInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket // 开启 Spring WebSocket
/**
 * 在类上，添加 @EnableWebSocket 注解，开启 Spring WebSocket 功能。
 * 实现 WebSocketConfigurer 接口，自定义 WebSocket 的配置。具体的，胖友可以看看 #registerWebSocketHandlers(registry) 方法，配置 WebSocket 处理器、拦截器，以及允许跨域。
 */
public class WebSocketConfiguration implements WebSocketConfigurer {

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(this.webSocketHandler(), "/") // 配置处理器
                .addInterceptors(new DemoWebSocketShakeInterceptor()) // 配置拦截器
                .setAllowedOrigins("*"); // 解决跨域问题
    }

    @Bean
    public DemoWebSocketHandler webSocketHandler() {
        return new DemoWebSocketHandler();
    }

    @Bean
    public DemoWebSocketShakeInterceptor webSocketShakeInterceptor() {
        return new DemoWebSocketShakeInterceptor();
    }

}
