package com.enjoyu.admin.controller;

import com.enjoyu.admin.common.CommonResponse;
import com.enjoyu.admin.controller.req.UserReq;
import com.enjoyu.admin.controller.vo.UserVo;
import com.enjoyu.admin.dao.entity.SysUser;
import com.enjoyu.admin.service.UserService;
import com.enjoyu.admin.shiro.ShiroKit;
import lombok.AllArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@AllArgsConstructor
@RequestMapping("sys")
public class SystemController {
    @RequiresRoles("admin")
    @GetMapping("user")
    public CommonResponse<UserVo> userInfo() {
        SysUser user = ShiroKit.getUser();
        UserVo userVo = new UserVo();
        return CommonResponse.success("", userVo);
    }

    @RequiresPermissions("admin")
    @PostMapping("user")
    public CommonResponse<Object> addUser(@RequestBody UserReq req) {
        userService.checkExists(req.getUserName());
        SysUser of = SysUser.of(req);
        userService.add(of);
        return CommonResponse.success("添加用户成功");
    }

    UserService userService;
}
