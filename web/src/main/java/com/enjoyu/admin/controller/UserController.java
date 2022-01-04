package com.enjoyu.admin.controller;

import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.enjoyu.admin.common.CommonResponse;
import com.enjoyu.admin.components.mbp.entity.User;
import com.enjoyu.admin.components.mbp.service.IUserService;
import com.enjoyu.admin.controller.req.UserListReq;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/user")
public class UserController {
    @Autowired
    IUserService userService;

    @GetMapping
    public String page() {
        return "admin/pages/users";
    }

    @ResponseBody
    @GetMapping("page")
    public CommonResponse<Page<User>> list(UserListReq req) {
        QueryChainWrapper<User> query = userService.query();
        if (StringUtils.hasText(req.getUsername())) {
            query.eq("username", req.getUsername());
        }
        if (StringUtils.hasText(req.getNickname())) {
            query.like("nickname", req.getNickname());
        }
        if (req.getStatus() != null) {
            query.eq("status", req.getStatus());
        }
        query.select("id", "username", "nickname", "phone", "mail", "status", "create_time");
        Page<User> page = query.page(req.getPage());
        return CommonResponse.data(page);
    }

}
