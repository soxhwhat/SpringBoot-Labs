package cn.iocoder.springcloudalibaba.labx04.sentineldemo.provider.controller;

import com.alibaba.csp.sentinel.Entry;
import com.alibaba.csp.sentinel.SphU;
import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/demo")
public class DemoController {

    // 测试【流量控制】
    @GetMapping("/echo")
    public String echo() {
        return "echo";
    }

    @GetMapping("/test")
    public String test() {
        return "test";
    }


    /**
     * 测试【熔断降级】
     * 除了流量控制以外，降低调用链路中的不稳定资源也是 Sentinel 的使命之一。由于调用关系的复杂性，如果调用链路中的某个资源出现了不稳定，最终会导致请求发生堆积。
     * 设计理念
     *
     * Sentinel 和 Hystrix 的原则是一致的: 当检测到调用链路中某个资源出现不稳定的表现，例如请求响应时间长或异常比例升高的时候，则对这个资源的调用进行限制，让请求快速失败，避免影响到其它的资源而导致级联故障。
     * 在限制的手段上，Sentinel 和 Hystrix 采取了完全不一样的方法。
     * Hystrix 通过 线程池隔离 的方式，来对依赖（在 Sentinel 的概念中对应 资源）进行了隔离。这样做的好处是资源和资源之间做到了最彻底的隔离。缺点是除了增加了线程切换的成本（过多的线程池导致线程数目过多），还需要预先给各个资源做线程池大小的分配。
     * Sentinel 对这个问题采取了两种手段：
     *
     * 1、通过并发线程数进行限制
     * 和资源池隔离的方法不同，Sentinel 通过限制资源并发线程的数量，来减少不稳定资源对其它资源的影响。这样不但没有线程切换的损耗，也不需要您预先分配线程池的大小。当某个资源出现不稳定的情况下，例如响应时间变长，对资源的直接影响就是会造成线程数的逐步堆积。当线程数在特定资源上堆积到一定的数量之后，对该资源的新请求就会被拒绝。堆积的线程完成任务后才开始继续接收请求。
     * 2、通过响应时间对资源进行降级
     * 除了对并发线程数进行控制以外，Sentinel 还可以通过响应时间来快速降级不稳定的资源。当依赖的资源出现响应时间过长后，所有对该资源的访问都会被直接拒绝，直到过了指定的时间窗口之后才重新恢复。
     * @return
     * @throws InterruptedException
     */
    @GetMapping("/sleep")
    public String sleep() throws InterruptedException {
        Thread.sleep(100L);
        return "sleep";
    }

    /**
     *测试【热点参数限流】
     *
     * 何为热点？热点即经常访问的数据。很多时候我们希望统计某个热点数据中访问频次最高的 Top K 数据，并对其访问进行限制。比如：
     *
     * 商品 ID 为参数，统计一段时间内最常购买的商品 ID 并进行限制。
     * 用户 ID 为参数，针对一段时间内频繁访问的用户 ID 进行限制。
     * 热点参数限流，会统计传入参数中的热点参数，并根据配置的限流阈值与模式，对包含热点参数的资源调用进行限流。热点参数限流可以看做是一种特殊的流量控制，仅对包含热点参数的资源调用生效
     *
     * Sentinel 利用 LRU 策略统计最近最常访问的热点参数，结合令牌桶算法来进行参数级别的流控。热点参数限流支持集群模式。
     */
    @GetMapping("/product_info")
    //在方法上，我们添加了 @SentinelResource 注解，自定义了 demo_product_info_hot 资源。
    /**
     * 为什么不直接使用 sentinel-spring-webmvc-adapter 库，自动给该 demo/product_info 接口生成的 /demo/product_info 资源呢？
     *
     * 原因：因为 sentinel-spring-webmvc-adapter 库提供的 SentinelWebInterceptor 拦截器在调用 Sentinel 客户端时，并未传入参数，所以无法进行热点参数限流
     * 解决：使用 Sentinel 提供的 @SentinelResource 注解，自定义了 demo_product_info_hot 资源。然后，通过 Spring AOP 拦截该方法的调用，实现 Sentinel 的处理逻辑。在本小节中，就是为了热点参数限流
     */
    @SentinelResource("demo_product_info_hot")
    public String productInfo(Integer id) {
        //这里，我们配置了当第一个参数的值为 1 时，限制在统计窗口中，请求最大次数为 1。
        return "商品编号：" + id;
    }

    /**
     * 系统自适应限流
     *
     * Sentinel 同时提供系统维度的自适应保护能力。防止雪崩，是系统防护中重要的一环。当系统负载较高的时候，如果还持续让请求进入，可能会导致系统崩溃，无法响应。在集群环境下，网络负载均衡会把本应这台机器承载的流量转发到其它的机器上去。如果这个时候其它的机器也处在一个边缘状态的时候，这个增加的流量就会导致这台机器也崩溃，最后导致整个集群不可用。
     *
     * 针对这个情况，Sentinel 提供了对应的保护机制，让系统的入口流量和系统的负载达到一个平衡，保证系统在能力范围之内处理最多的请求。
     * @return
     */

    // 测试「Sentinel 客户端 API」
    @GetMapping("/entry_demo")
    public String entryDemo() {
        Entry entry = null;
        try {
            // <1> 访问资源 调用 Sentinel 的 SphU#entry(String name) 方法，访问资源。其中，参数 name 就是在 Sentinel 中定义的资源名。如果访问资源被拒绝，例如说被限流或降级，则会抛出 BlockException 异常。
            entry = SphU.entry("entry_demo");

            // <2> ... 执行业务逻辑

            return "执行成功";
        } catch (BlockException ex) { // <3>
            return "被拒绝";
        } finally {
            // <4> 释放资源
            if (entry != null) {
                entry.exit(); //调用 Sentinel 的 Entry#exit() 方法，释放对资源的访问。
            }
        }
    }

    // 测试「Sentinel @SentinelResource 注解」

    /**
     * 我们使用 Sentinel 客户端 API，手动进行资源的保护。但是我们会发现，对代码的入侵太强，需要将业务逻辑进行修改。因此，Sentinel 提供了 @SentinelResource 注解声明自定义资源，通过 Spring AOP 拦截该注解的方法，自动调用 Sentinel 客户端 API，进行指定资源的保护。
     * @SentinelResource 用于定义资源，并提供可选的异常处理和 fallback 配置项。
     *
     * 特别地，若 blockHandler 和 fallback 都进行了配置，则被限流降级而抛出 BlockException 时只会进入 blockHandler 处理逻辑。若未配置 blockHandler、fallback 和 defaultFallback，则被限流降级时会将 BlockException 直接抛出
     * @return
     */
    @GetMapping("/annotations_demo")
    @SentinelResource(value = "annotations_demo_resource", //资源名称，必需项（不能为空）
            blockHandler = "blockHandler",
            fallback = "fallback")
    public String annotationsDemo(@RequestParam(required = false) Integer id) throws InterruptedException {
        if (id == null) {
            throw new IllegalArgumentException("id 参数不允许为空");
        }
        return "success...";
    }

    // BlockHandler 处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String blockHandler(Integer id, BlockException ex) {
        return "block：" + ex.getClass().getSimpleName();
    }

    // Fallback 处理函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String fallback(Integer id, Throwable throwable) {
        return "fallback：" + throwable.getMessage();
    }

}
