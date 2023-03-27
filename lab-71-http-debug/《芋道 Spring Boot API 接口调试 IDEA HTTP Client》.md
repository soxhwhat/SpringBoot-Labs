<http://www.iocoder.cn/Spring-Boot/IDEA-HTTP-Client/?github>
在 Spring Boot 项目中，我们通过 Profile 机制，实现不同环境，不同配置文件。在 IDEA HTTP Client 插件中，提供类似的机制，可以定义配置文件，抽取出不同环境的变量。

通过创建 http-client.env.json 配置文件，定义通用变量。例如说，url 地址、port 端口等等。
通过创建 http-client.private.env.json 配置文件，定义敏感变量。例如说，username/password 账号密码、token 访问令牌等等。
🔥 注意再注意，http-client.private.env.json 是定义定义敏感变量的配置文件，所以不要提交到 Git 仓库中！！！
