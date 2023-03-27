package cn.iocoder.springboot.lab25.springwebsocket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
// @EnableWebSocket // 无需添加该注解，因为我们并不是使用 Spring WebSocket
public class WebSocketConfiguration {

    /**
     * 在 #serverEndpointExporter() 方法中，创建 ServerEndpointExporter Bean 。该 Bean 的作用，是扫描添加有 @ServerEndpoint 注解的 Bean 。
     * @return
     */
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

}
