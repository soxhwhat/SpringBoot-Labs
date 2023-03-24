package cn.iocoder.springcloudalibaba.labx04.sentineldemo.provider;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoProviderApplication {

    /**
     * 流量控制，在网络传输中是一个常用的概念，它用于调整网络包的发送数据。然而，从系统稳定性角度考虑，在处理请求的速度上，也有非常多的讲究。任意时间到来的请求往往是随机不可控的，而系统的处理能力是有限的。我们需要根据系统的处理能力对流量进行控制。Sentinel 作为一个调配器，可以根据需要把随机的请求调整成合适的形状，如下图所示
     *
     * 流量控制有以下几个角度：
     * - 资源的调用关系：例如资源的调用链路，资源和资源之间的关系
     * - 运行指标，例如QPS、线程池、系统负载等
     * - 控制的效果，例如直接限流，冷启动，匀速排队等
     * @param args
     */
    public static void main(String[] args) {
        SpringApplication.run(DemoProviderApplication.class, args);
    }

}
