<http://www.iocoder.cn/Spring-Boot/config-file/?github>
如果在不同的目录中存在多个配置文件，它的读取顺序是：

1、config/application.properties（项目根目录中config目录下）
2、config/application.yml
3、application.properties（项目根目录下）
4、application.yml
5、resources/config/application.properties（项目resources目录中config目录下）
6、resources/config/application.yml
7、resources/application.properties（项目的resources目录下）
8、resources/application.yml
————————————————
版权声明：本文为CSDN博主「老周聊架构」的原创文章，遵循CC 4.0 BY-SA版权协议，转载请附上原文出处链接及本声明。
原文链接：https://blog.csdn.net/riemann_/article/details/108630781
