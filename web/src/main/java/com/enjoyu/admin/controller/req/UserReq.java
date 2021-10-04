package com.enjoyu.admin.controller.req;

import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class UserReq {
    private Long id;
    @NotBlank
    private String userName;
    @NotBlank
    private String password;
    private String mail;
    private String mobilePhone;

}
