<http://www.iocoder.cn/Spring-Boot/SpringMVC/?github>
### SpringMVC Ant路径匹配
#### Ant匹配规则
- SpringMVC的路径匹配规则是按照Ant来的，实际上不只是SpringMVC，整个Spring框架的路径解析都是按照Ant的风格来的。
- AntPathMatcher不仅可以匹配Spring的@RequestMapping路径，也可以用来匹配各种字符串，包括文件路径等。
#### 基本规则

wildcard	description
?	匹配任何单字符
*	匹配0或者任务数量的字符
**	匹配0或者更多的目录