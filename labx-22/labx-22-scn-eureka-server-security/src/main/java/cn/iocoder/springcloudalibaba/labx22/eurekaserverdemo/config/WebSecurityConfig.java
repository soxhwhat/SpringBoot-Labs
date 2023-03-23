package cn.iocoder.springcloudalibaba.labx22.eurekaserverdemo.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * 创建 WebSecurityConfig 配置类，设置 Eureka-Server 提供的 /eureka/** 无需传递 CSRF Token，代码如下
     * 默认配置下，Spring Security 要求每个强求需要携带 CSRF Token 才可以访问，而 Eureka-Client 是不会进行携带的，因此需要针对 /eureka/** 路径进行禁用，不然就要改 Eureka-Client 的源码
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().ignoringAntMatchers("/eureka/**");
        super.configure(http);
    }

}
