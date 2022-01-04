package com.enjoyu.admin.controller;

import com.enjoyu.admin.common.CommonResponse;
import com.enjoyu.admin.components.mbp.entity.User;
import com.enjoyu.admin.components.mbp.service.IUserService;
import com.enjoyu.admin.components.shiro.ShiroUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @GetMapping("/pswReset")
    public String page(Model model) {
        User user = ShiroUtil.currentUser();
        model.addAttribute("user", user);
        return "admin/pages/pswReset";
    }

    @ResponseBody
    @PostMapping("/pswReset")
    public CommonResponse<String> passwordChange(String oldPassword, String newPassword) {
        User user = ShiroUtil.currentUser();
        String salt = user.getSalt();
        String password = user.getPassword();
        if (!ShiroUtil.matchEncrypt(oldPassword, salt, password)) {
            return CommonResponse.error("密码错误");
        }
        String encrypt = ShiroUtil.encrypt(newPassword, salt);
        user.setPassword(encrypt);
        userService.updateById(user);
        return CommonResponse.success("密码修改成功");
    }

    @Autowired
    IUserService userService;
}
