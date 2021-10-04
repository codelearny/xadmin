package com.enjoyu.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_role")
public class SysRole {
    @TableId
    private Long id;
    private String roleName;
    private String description;
    private LocalDateTime createTime;
}
