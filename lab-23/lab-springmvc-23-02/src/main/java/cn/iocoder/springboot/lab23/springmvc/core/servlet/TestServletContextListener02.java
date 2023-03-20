package cn.iocoder.springboot.lab23.springmvc.core.servlet;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

/**
 在Java中，Filter和Listener都是基于Servlet API的一些特性。它们都可以用于在Web应用中拦截请求，并对请求做一些处理。它们的区别在于它们拦截请求的位置不同。

 Filter是一个Java Servlet API中的组件，主要用来拦截HTTP请求，并对请求进行处理。可以使用Filter来实现一些共同的需求，例如请求日志信息、身份验证、过滤内容等操作。Filter需要在Web应用中进行配置，可以映射到不同的URL模式。

 代码示例：

 @WebFilter(urlPatterns = {"/my-filter/*"})
 public class MyFilter implements Filter {

 public void init(FilterConfig filterConfig) throws ServletException {
 // 过滤器初始化方法
 }

 public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
 // 过滤器处理方法
 chain.doFilter(request, response);
 }

 public void destroy() {
 // 过滤器销毁方法
 }
 }


 Listener是一个Java Servlet API中的组件，用于在Web应用中监听不同类型的事件。Listener可以监听服务器的启动和关闭、Session的创建和销毁、Request的开始和结束等事件。可以使用Listener实现自己的业务需求，例如在HTTP请求开始前进行数据库连接、在Session创建时进行权限验证等。

 代码示例：

 @WebListener
 public class MyListener implements ServletContextListener {

 public void contextInitialized(ServletContextEvent servletContextEvent) {
 // Web应用启动时调用该方法
 }

 public void contextDestroyed(ServletContextEvent servletContextEvent) {
 // Web应用关闭时调用该方法
 }
 }


 总之，Filter和Listener的主要区别是，Filter主要用于拦截请求并进行处理，而Listener主要用于监听Web应用和Session的事件。
 */
@WebListener
public class TestServletContextListener02 implements ServletContextListener {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("[contextInitialized]");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }

}
