package com.enjoyu.admin.controller.req;

import lombok.Data;

@Data
public class XmlReq {
    private String name;
    private Integer tag;
    private UserReq user;
}
