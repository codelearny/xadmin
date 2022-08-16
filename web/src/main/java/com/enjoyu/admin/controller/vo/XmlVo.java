package com.enjoyu.admin.controller.vo;

import lombok.Data;

import java.util.List;

@Data
public class XmlVo {
    private String desc;
    private List<UserVo> users;
}
