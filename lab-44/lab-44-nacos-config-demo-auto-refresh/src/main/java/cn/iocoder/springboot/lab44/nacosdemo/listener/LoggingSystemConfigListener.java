package cn.iocoder.springboot.lab44.nacosdemo.listener;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigListener;
import com.alibaba.nacos.spring.util.parse.DefaultYamlConfigParse;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Properties;

/**
 * Nacos 配置监听器
 * 但是，在一些场景下，我们仍然需要自定义 Nacos 配置监听器，实现对 Nacos 配置的监听，执行自定义的逻辑。
 * 例如说，当数据库连接的配置发生变更时，我们需要通过监听该配置的变更，重新初始化应用中的数据库连接，从而访问到新的数据库地址。
 */

@Component
public class LoggingSystemConfigListener {

    /**
     * 日志配置项的前缀
     */
    private static final String LOGGER_TAG = "logging.level.";

    /**
     * loggingSystem 属性，是 Spring Boot Logger 日志系统，通过 LoggingSystem 可以进行日志级别的修改。
     */
    @Resource
    private LoggingSystem loggingSystem;

    /**
     * 在 #onChange(String newLog) 方法上，我们添加了 @NacosConfigListener 注解，声明该方法处理指定配置集的配置变化。
     * @param newLog
     * @throws Exception
     */
    @NacosConfigListener(dataId = "${nacos.config.data-id}", type = ConfigType.YAML, timeout = 5000)
    public void onChange(String newLog) throws Exception {
        // 使用 DefaultYamlConfigParse 工具类，解析配置
        Properties properties = new DefaultYamlConfigParse().parse(newLog);
        // 遍历配置集的每个配置项，判断是否是 logging.level 配置项
        for (Object t : properties.keySet()) {
            String key = String.valueOf(t);
            // 如果是 logging.level 配置项，则设置其对应的日志级别
            if (key.startsWith(LOGGER_TAG)) {
                // 获得日志级别
                String strLevel = properties.getProperty(key, "info");
                LogLevel level = LogLevel.valueOf(strLevel.toUpperCase());
                // 设置日志级别到 LoggingSystem 中
                loggingSystem.setLogLevel(key.replace(LOGGER_TAG, ""), level);
            }
        }
    }

}
