package com.enjoyu.admin.controller.req;

import lombok.Data;

@Data
public class RoleReq {
    private Long id;
    private String roleName;
    private String description;
}
