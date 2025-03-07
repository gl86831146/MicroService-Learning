package top.gsc.userservice.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import top.gsc.userservice.entity.User;

@Mapper
public interface UserMapper extends BaseMapper<User> {
    User selectById(@Param("id") Long id);
}
