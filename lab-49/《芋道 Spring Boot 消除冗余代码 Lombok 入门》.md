<http://www.iocoder.cn/Spring-Boot/Lombok/?github>
#### Lombok注解一览
Lombok 的注解非常多，我们逐个来看看。

@Getter 注解，添加在类或属性上，生成对应的 get 方法。

@Setter 注解，添加在类或属性上，生成对应的 set 方法。

@ToString 注解，添加在类上，生成 toString 方法。

@EqualsAndHashCode 注解，添加在类上，生成 equals 和 hashCode 方法。

@AllArgsConstructor、@RequiredArgsConstructor、@NoArgsConstructor 注解，添加在类上，为类自动生成对应参数的构造方法。

@Data 注解，添加在类上，是 5 个 Lombok 注解的组合。

为所有属性，添加 @Getter、@ToString、@EqualsAndHashCode 注解的效果
为非 final 修饰的属性，添加 @Setter 注解的效果
为 final 修改的属性，添加 @RequiredArgsConstructor 注解的效果
@Value 注解，添加在类上，和 @Data 注解类似，区别在于它会把所有属性默认定义为 private final 修饰，所以不会生成 set 方法。

@CommonsLog、@Flogger、@Log、@JBossLog、@Log4j、@Log4j2、@Slf4j、@Slf4jX 注解，添加在类上，自动为类添加对应的日志支持。

@NonNull 注解，添加在方法参数、类属性上，用于自动生成 null 参数检查。若确实是 null 时，抛出 NullPointerException 异常。

@Cleanup 注解，添加在方法中的局部变量上，在作用域结束时会自动调用 #close() 方法，来释放资源。例如说，使用在 Java IO 流操作的时候。

@Builder 注解，添加在类上，给该类加个构造者模式 Builder 内部类。

@Synchronized 注解，添加在方法上，添加同步锁。

@SneakyThrows 注解，添加在方法上，给该方法添加 try catch 代码块。

@Accessors 注解，添加在方法或属性上，并设置 chain = true，实现链式编程。

#### @Data 注解
@Data 注解，添加在类上，是 5 个 Lombok 注解的组合。

为所有属性，添加 @Getter、@ToString、@EqualsAndHashCode 注解的效果
为非 final 修饰的属性，添加 @Setter 注解的效果
为 final 修改的属性，添加 @RequiredArgsConstructor 注解的效果

不过要注意，如果使用 @Data 注解的类，继承成了其它父类的属性，最好额外添加 @ToString(callSuper = true) 和 @EqualsAndHashCode(callSuper = true) 注解。

因为默认情况下，@Data 注解不会处理父类的属性。所以需要我们通过 callSuper = true 属性，声明需要调用父类对应的方法。
这个情况非常常见，例如说在实体类中，我们可能会声明一个抽象父类 AbstractEntity，而该类里有一个 id 编号属性。

