package cn.iocoder.springcloud.labx08.gatewaydemo.filter;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.HashMap;
import java.util.Map;

/**
 * 一般情况下，我们在 Gateway 上会去做的拓展，主要集中在 Filter 上，例如说接入认证服务。因此，我们来搭建 Gateway 自定义 Filter 实现的示例，提供“伪劣”的认证功能。
 * 创建认证Filter工厂
 */
@Component
//将泛型参数 <C> 设置为我们定义的 AuthGatewayFilterFactory.Config 配置类。这样，Gateway 在解析配置时，会转换成 Config 对象。
public class AuthGatewayFilterFactory extends AbstractGatewayFilterFactory<AuthGatewayFilterFactory.Config> {

    public AuthGatewayFilterFactory() {
        super(AuthGatewayFilterFactory.Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        // token 和 userId 的映射
        Map<String, Integer> tokenMap = new HashMap<>();
        tokenMap.put("yunai", 1);

        // 创建 GatewayFilter 对象
        return new GatewayFilter() {

            @Override
            public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
                // 获得 token
                ServerHttpRequest request = exchange.getRequest();
                HttpHeaders headers = request.getHeaders();
                //认证模块在验证用户身份时通常会从请求的header中读取access-token，然后对其进行验证。在这个过程中，认证模块需要知道应该从哪个header中获取access-token。这时，就可以调用config.getTokenHeaderName()函数获取应用配置中指定的header名称，然后在请求中查找该header，最终获取到access-token的值。
                String token = headers.getFirst(config.getTokenHeaderName());

                // 如果没有 token，则不进行认证。因为可能是无需认证的 API 接口
                if (!StringUtils.hasText(token)) {
                    return chain.filter(exchange);
                }

                // 进行认证
                ServerHttpResponse response = exchange.getResponse();
                Integer userId = tokenMap.get(token);

                // 通过 token 获取不到 userId，说明认证不通过
                if (userId == null) {
                    // 响应 401 状态码
                    response.setStatusCode(HttpStatus.UNAUTHORIZED);
                    // 响应提示
                    DataBuffer buffer = exchange.getResponse().bufferFactory().wrap("认证不通过".getBytes());
                    return response.writeWith(Flux.just(buffer));
                }

                // 认证通过，将 userId 添加到 Header 中
                request = request.mutate().header(config.getUserIdHeaderName(), String.valueOf(userId))
                        .build();
                return chain.filter(exchange.mutate().request(request).build());
            }

        };
    }

    public static class Config {

        private static final String DEFAULT_TOKEN_HEADER_NAME = "token";
        private static final String DEFAULT_HEADER_NAME = "user-id";

        //tokenHeaderName 属性：认证 Token 的 Header 名字，默认值为 token。
        private String tokenHeaderName = DEFAULT_TOKEN_HEADER_NAME;
        //userIdHeaderName 属性：认证后的 UserId 的 Header 名字，默认为 user-id。
        private String userIdHeaderName = DEFAULT_HEADER_NAME;

        public String getTokenHeaderName() {
            return tokenHeaderName;
        }

        public String getUserIdHeaderName() {
            return userIdHeaderName;
        }

        public Config setTokenHeaderName(String tokenHeaderName) {
            this.tokenHeaderName = tokenHeaderName;
            return this;
        }

        public Config setUserIdHeaderName(String userIdHeaderName) {
            this.userIdHeaderName = userIdHeaderName;
            return this;
        }

    }

}
