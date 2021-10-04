package com.enjoyu.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;

@Data
@TableName("sys_role_menu")
public class SysRoleMenu {
    @TableId
    private Long id;
    private Long menuId;
    private String menuName;
    private Long roleId;
    private String roleName;
    private String state;
    private Date createTime;
}
