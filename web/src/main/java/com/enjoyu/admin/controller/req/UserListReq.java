package com.enjoyu.admin.controller.req;

import lombok.Data;

@Data
public class UserListReq implements PageReq {
    private String username;
    private String nickname;
    private Integer status;
    private Integer pageIndex;
    private Integer pageSize;


}
