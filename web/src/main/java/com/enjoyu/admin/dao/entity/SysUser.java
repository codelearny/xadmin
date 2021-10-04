package com.enjoyu.admin.dao.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.enjoyu.admin.controller.req.UserReq;
import com.enjoyu.admin.shiro.ShiroKit;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@TableName("sys_user")
public class SysUser {
    @TableId
    private Long id;
    private String userName;
    private String password;
    private String salt;
    private String mail;
    private String mobilePhone;
    private Integer state;
    private LocalDateTime createTime;
    private LocalDateTime lockTime;

    public static SysUser of(UserReq req) {
        SysUser sysUser = new SysUser();
        sysUser.setUserName(req.getUserName());
        sysUser.setMail(req.getMail());
        sysUser.setMobilePhone(req.getMobilePhone());
        String password = req.getPassword();
        String salt = ShiroKit.getRandomSalt(5);
        String pwd = ShiroKit.md5(password, salt);
        sysUser.setPassword(pwd);
        return sysUser;
    }
}
