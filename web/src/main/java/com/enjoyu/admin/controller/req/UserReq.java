package com.enjoyu.admin.controller.req;

import com.enjoyu.admin.components.mbp.entity.User;
import com.enjoyu.admin.components.shiro.ShiroUtil;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
public class UserReq {

    private Integer id;
    /**
     * 用户名
     */
    @NotBlank
    private String username;

    /**
     * 密码
     */
    @NotBlank
    private String password;

    /**
     * 昵称
     */
    @NotBlank
    private String nickname;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 邮件地址
     */
    private String mail;

    public User toEntity() {
        User user = new User();
        user.setId(getId());
        user.setUsername(getUsername());
        String password = getPassword();
        String randomSalt = ShiroUtil.randomSalt();
        String encrypt = ShiroUtil.encrypt(password, randomSalt);
        user.setPassword(encrypt);
        user.setSalt(randomSalt);
        user.setNickname(getNickname());
        user.setPhone(getPhone());
        user.setMail(getMail());
        user.setCreateTime(LocalDateTime.now());
        return user;
    }
}
