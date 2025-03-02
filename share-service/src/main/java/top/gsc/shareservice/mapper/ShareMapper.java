package top.gsc.shareservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import top.gsc.shareservice.model.entity.Share;

@Mapper
public interface ShareMapper extends BaseMapper<Share> {
}
