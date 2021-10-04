package com.enjoyu.admin.controller.req;

import lombok.Data;

@Data
public class MenuReq {
    private Long id;
    private String menuName;
    private String description;
    private String level;
    private Long parentId;
    private String icon;
    private String url;
}
