package cn.iocoder.springboot.lab12.mybatis.mapper;

import cn.iocoder.springboot.lab12.mybatis.dataobject.UserDO;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Date;
import java.util.List;

@Repository
public interface UserMapper extends BaseMapper<UserDO> {

    default UserDO selectByUsername(@Param("username") String username) {
//       我们使用了 com.baomidou.mybatisplus.core.conditions.query.QueryWrapper<T> 构造相对灵活的条件，这样一些动态 SQL 我们就无需在 XML 中编写。
//        建议 1 ：不要在 Service 中，使用 QueryWrapper 拼接动态条件。因为 BaseMapper 提供了 #selectList(Wrapper<T> queryWrapper) 等方法，促使我们能够在 Service 层的逻辑中，使用 QueryWrapper 拼接动态条件，这样会导致逻辑里遍布了各种查询，使我们无法对实际有哪些查询条件做统一的管理。碰到这种情况，建议封装到对应的 Mapper 中，这样会更加简洁干净可管理。
//         建议 2 ：因为 QueryWrapper 暂时不支持一些类似 <if /> 等 MyBatis 的 OGNL 表达式，所以艿艿在 onemall 中，通过继承 QueryWrapper 类，封装了 QueryWrapperX 类。
        return selectOne(new QueryWrapper<UserDO>().eq("username", username));
    }

    List<UserDO> selectByIds(@Param("ids") Collection<Integer> ids);

    default IPage<UserDO> selectPageByCreateTime(IPage<UserDO> page, @Param("createTime") Date createTime) {
//        #selectPageByCreateTime(IPage<UserDO> page, @Param("createTime") Date createTime) 方法，是我们额外添加的，用于演示 MyBatis-Plus 提供的分页插件。
        return selectPage(page,
                new QueryWrapper<UserDO>().gt("create_time", createTime)
//                new QueryWrapper<UserDO>().like("username", "46683d9d")
        );
    }

}
