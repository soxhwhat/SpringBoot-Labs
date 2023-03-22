package cn.iocoder.springboot.lab44.nacosdemo.properties;

import com.alibaba.nacos.api.config.ConfigType;
import com.alibaba.nacos.api.config.annotation.NacosConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
//这里有一点要注意，nacos.config.auto-refresh 配置项开启的是全局的，必须为 true 时，才能使用自动刷新配置的功能。同时，每个 @NacosConfigurationProperties 或 @NacosValue 注解，也需要设置 autoRefreshed 属性为 true 时，对应的配置项才会自动刷新。
@NacosConfigurationProperties(prefix = "", dataId = "${nacos.config.data-id}", type = ConfigType.YAML, autoRefreshed = true)
public class TestProperties {

    /**
     * 测试属性
     */
    private String test;

    public String getTest() {
        return test;
    }

    public TestProperties setTest(String test) {
        this.test = test;
        return this;
    }

}
