package cn.iocoder.springcloudalibaba.labx04.sentineldemo.provider.web;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

@Component
public class CustomRequestOriginParser implements RequestOriginParser {

    /**
     * 在 Sentinel 的子项目 sentinel-spring-webmvc-adapter 中，定义了 RequestOriginParser 接口，从请求中解析到调用来源，例如说使用 IP、请求头 user、请求头 appName。
     *
     * 因为我们要使用 Sentinel 黑白名单控制的功能，所以需要获得请求的调用来。RequestOriginParser 暂时没有提供默认的实现，所以我们自定义 CustomRequestOriginParser 实现类，解析请求头 s-user 作为调用来源。
     * @param request
     * @return
     */
    @Override
    public String parseOrigin(HttpServletRequest request) {
        // <X> 从 Header 中，获得请求来源
        String origin = request.getHeader("s-user");
        // <Y> 如果为空，给一个默认的
        if (StringUtils.isEmpty(origin)) {
            origin = "default";
        }
        return origin;
    }

}
