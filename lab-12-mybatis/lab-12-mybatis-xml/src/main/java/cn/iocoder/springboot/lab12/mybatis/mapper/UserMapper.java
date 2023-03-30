package cn.iocoder.springboot.lab12.mybatis.mapper;

import cn.iocoder.springboot.lab12.mybatis.dataobject.UserDO;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface UserMapper {

    int insert(UserDO user);

    int updateById(UserDO user);

    int deleteById(@Param("id") Integer id); // 生产请使用标记删除，除非有点想不开，嘿嘿。

    UserDO selectById(@Param("id") Integer id);

    UserDO selectByUsername(@Param("username") String username);//@Param 注解，声明变量名。
//    在方法为单参数时，非必须。
//    在方法为多参数时，必须。艿艿自己的编程习惯，禁止使用 Map 作为查询参数，因为无法通过方法的定义，很直观的看懂具体的用途。

    List<UserDO> selectByIds(@Param("ids")Collection<Integer> ids);

}
