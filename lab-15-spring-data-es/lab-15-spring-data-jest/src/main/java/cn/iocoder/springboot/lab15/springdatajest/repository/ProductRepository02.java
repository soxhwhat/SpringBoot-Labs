package cn.iocoder.springboot.lab15.springdatajest.repository;

import cn.iocoder.springboot.lab15.springdatajest.dataobject.ESProductDO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface ProductRepository02 extends ElasticsearchRepository<ESProductDO, Integer> {

    /**
     * 在 Spring Data 中，支持根据方法名作生成对应的查询（WHERE）条件，进一步进化我们使用 JPA ，具体是方法名以 findBy、existsBy、countBy、deleteBy 开头，后面跟具体的条件。
     * @param name
     * @return
     */
    ESProductDO findByName(String name);

    Page<ESProductDO> findByNameLike(String name, Pageable pageable);

}
