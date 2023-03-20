package cn.iocoder.springboot.lab23.springmvc.core.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

/**
 * Servlet 是一种 Java 类，用于处理客户端请求并生成响应。它可以接收 HTTP 请求并返回 HTML 页面、XML 数据或其他格式的数据。Servlet 通常被用来实现 Web 应用程序的业务逻辑。
 *
 * Filter 是一种 Java 类，用于在 Servlet 执行之前或之后拦截请求和响应。它可以对请求进行修改或过滤，并对响应进行处理。Filter 可以用于实现安全性、日志记录、性能监控等功能。
 *
 * Listener 是一种 Java 接口，用于监听 Web 应用程序中的事件。它可以监听 ServletContext、HttpSession 和 ServletRequest 等对象的创建和销毁事件，并在事件发生时执行相应的操作。Listener 可以用于实现 Web 应用程序的初始化和清理工作，以及在运行时监控应用程序状态等功能。
 *
 * 这三个组件都是基于 Java Servlet API 实现的，是构建 Java Web 应用程序不可缺少的组成部分。
 */

@WebFilter("/test/*")
public class TestFilter02 implements Filter {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        logger.info("[doFilter]");
        filterChain.doFilter(servletRequest, servletResponse);
    }

}
