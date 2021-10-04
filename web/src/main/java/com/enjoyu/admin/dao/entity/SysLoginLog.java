package com.enjoyu.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_login_log")
public class SysLoginLog {
    @TableId
    private Long id;
    private Long userId;
    private String userName;
    private Short loginStatus;
    private LocalDateTime loginTime;
    private String loginMessage;
}
