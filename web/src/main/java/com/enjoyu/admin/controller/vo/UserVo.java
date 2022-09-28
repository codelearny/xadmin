package com.enjoyu.admin.controller.vo;

import com.enjoyu.admin.components.mbp.entity.User;
import lombok.Data;

import java.util.List;

@Data
public class UserVo {
    private Long id;
    private String username;
    private String mail;
    private String mobilePhone;
    private List<String> roles;

    public static UserVo from(User user) {
        UserVo userVo = new UserVo();
        userVo.setId(user.getId());
        userVo.setUsername(user.getUsername());
        userVo.setMail(user.getMail());
        userVo.setMobilePhone(user.getPhone());
        return userVo;
    }
}
