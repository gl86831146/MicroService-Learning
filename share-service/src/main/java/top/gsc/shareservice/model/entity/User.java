package top.gsc.shareservice.model.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

@Data
public class User {
    private Long id;

    @TableField("user_name")
    private String username;

    @TableField("avatar_url")
    private String avatarUrl;
}
