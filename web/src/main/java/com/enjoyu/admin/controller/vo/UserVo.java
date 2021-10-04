package com.enjoyu.admin.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class UserVo {
    private Long id;
    private String userName;
    private String mail;
    private String mobilePhone;
    private List<String> roles;
}
