package com.enjoyu.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_user_role")
public class SysUserRole {
    @TableId
    private Long id;
    private Long userId;
    private Long roleId;
    private String userName;
    private Date createTime;

}
