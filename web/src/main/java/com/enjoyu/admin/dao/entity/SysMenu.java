package com.enjoyu.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_menu")
public class SysMenu {
    @TableId
    private Long id;
    private String menuName;
    private String description;
    private String level;
    private Long parentId;
    private String icon;
    private String url;
    private String status;
    private LocalDateTime createTime;
}
