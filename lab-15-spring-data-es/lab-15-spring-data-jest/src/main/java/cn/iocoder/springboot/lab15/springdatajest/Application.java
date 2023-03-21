package cn.iocoder.springboot.lab15.springdatajest;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchAutoConfiguration;
import org.springframework.boot.autoconfigure.data.elasticsearch.ElasticsearchDataAutoConfiguration;
//需要排除 ElasticsearchAutoConfiguration 和 ElasticsearchDataAutoConfiguration 自动配置类，否则会自动配置 Spring Data Elasticsearch
@SpringBootApplication(exclude = {ElasticsearchAutoConfiguration.class, ElasticsearchDataAutoConfiguration.class})
public class Application {

}
