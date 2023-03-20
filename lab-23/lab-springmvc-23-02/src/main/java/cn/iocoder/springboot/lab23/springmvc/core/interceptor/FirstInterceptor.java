package cn.iocoder.springboot.lab23.springmvc.core.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Filter和HandlerInterceptor都是Spring MVC中的拦截器，用于在请求到达Controller之前或之后进行一些处理。
 *
 * 区别：
 *
 * 1. Filter是Servlet规范中的一部分，而HandlerInterceptor是Spring MVC框架提供的。
 *
 * 2. Filter可以拦截所有的请求，包括静态资源请求，而HandlerInterceptor只能拦截Controller中定义的请求。
 *
 * 3. Filter可以修改HttpServletRequest和HttpServletResponse对象，而HandlerInterceptor只能修改HttpServletRequest对象。
 *
 * 4. Filter可以在web.xml中配置顺序，多个Filter按照配置顺序依次执行；而HandlerInterceptor需要在代码中指定执行顺序。
 *
 * 联系：
 *
 * 1. Filter和HandlerInterceptor都可以用于实现权限控制、日志记录、字符编码转换等功能。
 *
 * 2. 在使用上，Filter通常用于全局过滤器的设置，如字符编码转换、安全过滤等；而HandlerInterceptor通常用于业务逻辑处理前后的操作，如登录验证、日志记录等。
 */
public class FirstInterceptor implements HandlerInterceptor {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        logger.info("[preHandle][handler({})]", handler);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        logger.info("[postHandle][handler({})]", handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        logger.info("[afterCompletion][handler({})]", handler, ex);
    }

}
