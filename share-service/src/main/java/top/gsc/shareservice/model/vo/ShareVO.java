package top.gsc.shareservice.model.vo;

import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ShareVO {
    private Long id;

    @TableField("user_id")
    private Long userId;

    private String title;

    @TableField("create_time")
    private LocalDateTime createTime;

    @TableField("update_time")
    private LocalDateTime updateTime;

    @TableField("is_original")
    private Boolean isOriginal;

    private String author;

    private String cover;

    private String summary;

    private Long price;

    @TableField("download_url")
    private String downloadUrl;

    @TableField("buy_count")
    private Long buyCount;

    @TableField("show_flag")
    private Boolean showFlag;

    @TableField("audit_status")
    private String auditStatus;

    private String reason;

   private UserVO user;
}
