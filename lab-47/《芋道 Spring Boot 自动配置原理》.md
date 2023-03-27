<http://www.iocoder.cn/Spring-Boot/autoconfigure/?github>

### 概述
Spring Boot 自动配置，顾名思义，是希望能够自动配置，将我们从配置的苦海中解脱出来。那么既然要自动配置，它需要解三个问题：

满足什么样的条件？ 因为我们引入了 spring-boot-starter-web 依赖。
创建哪些 Bean？ 创建了一个内嵌的 Tomcat Bean，并进行启动。
创建的 Bean 的属性？ 通过 application.yaml 配置文件的 server.port 配置项，定义 Tomcat Bean 的启动端口属性，并且默认值为 8080。

① 配置类
② 条件注解
1. 在类上添加了 @ConditionalOnWebApplication 条件注解，表示当前配置类需要在当前项目是 Web 项目的条件下，才能生效。在 Spring Boot 项目中，会将项目类型分成 Web 项目（使用 SpringMVC 或者 WebFlux）和非 Web 项目。这样我们就很容易理解，为什么 EmbeddedWebServerFactoryCustomizerAutoConfiguration 配置类会要求在项目类型是 Web 项目，只有 Web 项目才有必要创建内嵌的 Web 服务器呀。
2. 在类上添加了 @ConditionalOnClass 条件注解，表示当前配置类需要在当前项目有指定类的条件下，才能生效。

TomcatWebServerFactoryCustomizerConfiguration 配置类，需要有 tomcat-embed-core 依赖提供的 Tomcat、UpgradeProtocol 依赖类，才能创建内嵌的 Tomcat 服务器。
JettyWebServerFactoryCustomizer 配置类，需要有 jetty-server 依赖提供的 Server、Loader、WebAppContext 类，才能创建内嵌的 Jetty 服务器。
③ 配置属性
使用 @EnableConfigurationProperties 注解，让 ServerProperties 配置属性类生效。在 Spring Boot 定义了 @ConfigurationProperties 注解，用于声明配置属性类，将指定前缀的配置项批量注入到该类中。****
旁白君：这里其实还有一个非常有意思的话题，作为拓展知识，胖友可以后续去看看。实际上，我们可以把 spring.factories 理解成 Spring Boot 自己的 SPI 机制。感兴趣的胖友，

#### Java SPI
##### spring中SPI机制实现
1.SPI机制
    1.SPI思想
    - SPI全称为Service Provider Interface，即服务提供者接口，是JDK内置的一种服务提供发现机制。
    - SPI的思想：系统里抽象的各个模块，往往有很多不同的实现方案，比如日志模块的方案，xml解析模块、jdbc模块的方案等。面向的对象的设计里，我们一般推荐模块之间基于接口编程，模块之间不对实现类进行硬编码。一旦代码里涉及具体的实现类，就违反了可拔插的原则，如果需要替换一种实现，就需要修改代码。为了实现在模块装配的时候能不在程序里动态指明，这就需要一种服务发现机制。java spi就是提供这样的一个机制：为某个接口寻找服务实现的机制
    2.SPI约定
    - 当服务的提供者，提供了服务接口的一种实现之后，在jar包的META-INF/services/目录里同时创建一个以服务接口命名的文件。该文件里就是实现该服务接口的具体实现类。而当外部程序装配这个模块的时候，就能通过该jar包META-INF/services/里的配置文件找到具体的实现类名，并装载实例化，完成模块的注入。通过这个约定，就不需要把服务放在代码中了，通过模块被装配的时候就可以发现服务类了。
2. SPI使用案例
- common-logging apache最早提供的日志的门面接口。只有接口，没有实现。具体方案由各提供商实现， 发现日志提供商是通过扫描 META-INF/services/org.apache.commons.logging.LogFactory配置文件，通过读取该文件的内容找到日志提工商实现类。只要我们的日志实现里包含了这个文件，并在文件里制定 LogFactory工厂接口的实现类即可。
3. springboot中的类SPI扩展机制
- 在springboot的自动装配过程中，最终会加载MET
- A-INF/spring.factories文件，而加载的过程是由SpringFactoriesLoader加载的。从CLASSPATH下的每个Jar包中搜寻所有META-INF/spring.factories配置文件，然后将解析properties文件，找到指定名称的配置后返回。需要注意的是，其实这里不仅仅是会去ClassPath路径下查找，会扫描所有路径下的Jar包，只不过这个文件只会在Classpath下的jar包中。
4. class.forName(“com.mysql.jdbc.Driver”)到底做了什么事？
class.forName与类加载机制有关，会触发执行com.mysql.jdbc.Driver类中的静态方法，从而使主类加载数据库驱动。如果再追问，为什么它的静态块没有自动触发？可答：因为数据库驱动类的特殊性质，JDBC规范中明确要求Driver类必须向DriverManager注册自己，导致其必须由class.forName手动触发，这可以在java.sql.Driver中得到解释
   应用程序不再需要使用 Class.forName() 显式地加载 JDBC 驱动程序。当前使用 Class.forName() 加载 JDBC 驱动程序的现有程序将在不作修改的情况下继续工作
   Class.forName已经被弃用了，所以，这道题目的最佳回答，应当是和面试官牵扯到JAVA中的SPI机制，进而聊聊加载驱动的演变历史。
#### 自动配置类
在 Spring Boot 的 spring-boot-autoconfigure 项目，提供了大量框架的自动配置，如下图所示：
![](![img.png](img.png))
在我们通过 SpringApplication#run(Class<?> primarySource, String... args) 方法，启动 Spring Boot 应用的时候，有个非常重要的组件 SpringFactoriesLoader 类，会读取 META-INF 目录下的 spring.factories 文件，获得每个框架定义的需要自动配置的配置类。

我们以 spring-boot-autoconfigure 项目的 Spring Boot spring.factories 文件来举个例子，如下图所示：
![](![img_1.png](img_1.png))
如此，原先 @Configuration 注解的配置类，就升级成类自动配置类。这样，Spring Boot 在获取到需要自动配置的配置类后，就可以自动创建相应的 Bean，完成自动配置的功能。
#### 条件注解
在 Spring Boot 中，提供了大量的条件注解，用于判断当前项目是否满足某些条件，从而决定是否需要自动配置。这些条件注解，都是基于 Spring 的条件注解，进行了扩展。
@ConditionalOnBean：当容器里有指定 Bean 的条件下
@ConditionalOnMissingBean：当容器里没有指定 Bean 的情况下
@ConditionalOnSingleCandidate：当指定 Bean 在容器中只有一个，或者虽然有多个但是指定首选 Bean
@ConditionalOnClass：当类路径下有指定类的条件下
@ConditionalOnMissingClass：当类路径下没有指定类的条件下
@ConditionalOnProperty：指定的属性是否有指定的值
@ConditionalOnResource：类路径是否有指定的值
@ConditionalOnExpression：基于 SpEL 表达式作为判断条件
@ConditionalOnJava：基于 Java 版本作为判断条件
@ConditionalOnJndi：在 JNDI 存在的条件下差在指定的位置
@ConditionalOnNotWebApplication：当前项目不是 Web 项目的条件下
@ConditionalOnWebApplication：当前项目是 Web项 目的条件下

#### 内置Starter
我们在使用 Spring Boot 时，并不会直接引入 spring-boot-autoconfigure 依赖，而是使用 Spring Boot 内置提供的 Starter 依赖。例如说，我们想要使用 SpringMVC 时，引入的是 spring-boot-starter-web 依赖。这是为什么呢？

因为 Spring Boot 提供的自动配置类，基本都有 @ConditionalOnClass 条件注解，判断我们项目中存在指定的类，才会创建对应的 Bean。而拥有指定类的前提，一般是需要我们引入对应框架的依赖。

因此，在我们引入 spring-boot-starter-web 依赖时，它会帮我们自动引入相关依赖，从而保证自动配置类能够生效，创建对应的 Bean。
#### 自定义Starter
在一些场景下，我们需要自己实现自定义 Starter 来达到自动配置的目的。例如说：

三方框架并没有提供 Starter，比如说 Swagger、XXL-JOB 等。
Spring Boot 内置的 Starter 无法满足自己的需求，比如说 spring-boot-starter-jdbc 不提供多数据源的配置。
随着项目越来越大，想要提供适合自己团队的 Starter 来方便配置项目，比如说永辉彩食鲜 csx-bsf-all 项目。

命名规则：

场景	命名规则	示例
Spring Boot 内置 Starter	spring-boot-starter-{框架}	spring-boot-starter-web
框架 自定义 Starter	{框架}-spring-boot-starter	mybatis-spring-boot-starter
公司 自定义 Starter	{公司}-spring-boot-starter-{框架}	暂无，艿艿自己的想法哈

创建 yunai-server-spring-boot-starter 项目，实现一个 Java 内置 HttpServer 服务器的自动化配置。考虑到示例比较简单，我们就不像 Spring Boot 拆分成 spring-boot-autoconfigure 和 spring-boot-starter-{框架} 两个项目。

1. 引入Spring Boot Starter依赖
2. 创建 YunaiServerProperties 配置属性类，读取 yunai.server 前缀的配置项。
3. 创建 YunaiServerAutoConfiguration 自动配置类，实现自动配置的功能。
```java
@Configuration // 声明配置类
@EnableConfigurationProperties(YunaiServerProperties.class) // 使 YunaiServerProperties 配置属性类生效
public class YunaiServerAutoConfiguration {

    private Logger logger = LoggerFactory.getLogger(YunaiServerAutoConfiguration.class);

    @Bean // 声明创建 Bean
    @ConditionalOnClass(HttpServer.class) // 需要项目中存在 com.sun.net.httpserver.HttpServer 类。该类为 JDK 自带，所以一定成立。
    public HttpServer httpServer(YunaiServerProperties serverProperties) throws IOException {
        // 创建 HttpServer 对象，并启动
        HttpServer server = HttpServer.create(new InetSocketAddress(serverProperties.getPort()), 0);
        server.start();
        logger.info("[httpServer][启动服务器成功，端口为:{}]", serverProperties.getPort());

        // 返回
        return server;
    }

}
```
4. 创建 META-INF/spring.factories 文件，声明自动配置类。
```
org.springframework.boot.autoconfigure.EnableAutoConfiguration=\
cn.iocoder.springboot.lab47.yunaiserver.autoconfigure.YunaiServerAutoConfiguration
```


### Spring Boot Jar 启动原理
1. 概述
   Spring Boot 提供了 Maven 插件 spring-boot-maven-plugin，可以方便的将 Spring Boot 项目打成 jar 包或者 war 包。

考虑到部署的便利性，我们绝大多数 99.99% 的场景下，我们会选择打成 jar 包。这样，我们就无需在部署项目的服务器上，配置相应的 Tomcat、Jetty 等 Servlet 容器。

那么，jar 包是如何运行，并启动 Spring Boot 项目的呢？这个就是本文的目的，一起弄懂 Spring Boot jar 包的运行原理。
![](![img_2.png](img_2.png))
- META-INF 目录：通过 MANIFEST.MF 文件提供 jar 包的元数据，声明了 jar 的启动类。

- org 目录：为 Spring Boot 提供的 spring-boot-loader 项目，它是 java -jar 启动 Spring Boot 项目的秘密所在，也是稍后我们将深入了解的部分。

Spring Boot Loader provides the secret sauce that allows you to build a single jar file that can be launched using java -jar. Generally you will not need to use spring-boot-loader directly, but instead work with the Gradle or Maven plugin.

- BOOT-INF/lib 目录：我们 Spring Boot 项目中引入的依赖的 jar 包们。spring-boot-loader 项目很大的一个作用，就是解决 jar 包里嵌套 jar 的情况，如何加载到其中的类。

- BOOT-INF/classes 目录：我们在 Spring Boot 项目中 Java 类所编译的 .class、配置文件等等。
2. MANIFEST.MF
3. JarLauncher
4. LaunchedURLClassLoader