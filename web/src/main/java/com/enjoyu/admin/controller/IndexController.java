package com.enjoyu.admin.controller;

import com.enjoyu.admin.common.CommonResponse;
import com.enjoyu.admin.common.exception.ErrEnum;
import com.enjoyu.admin.common.exception.ServiceException;
import com.enjoyu.admin.components.mbp.entity.User;
import com.enjoyu.admin.components.mbp.service.IUserService;
import com.enjoyu.admin.controller.req.UserReq;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

@Slf4j
@Controller
public class IndexController {

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        return "index";
    }

    @GetMapping({"/login"})
    public String login() {
        return "login";
    }

    @ResponseBody
    @PostMapping(value = "/login")
    public CommonResponse<Object> login(@NotBlank String username, @NotBlank String password, @NotBlank String verifyCode, HttpServletRequest request) {
        if (!CaptchaUtil.ver(verifyCode, request)) {
            CaptchaUtil.clear(request);
            throw new ServiceException(ErrEnum.VERIFY_CODE_ERROR);
        }

        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username, password);
        SecurityUtils.getSubject().login(usernamePasswordToken);

        return CommonResponse.success("登录成功");
    }


    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48, 2);
        CaptchaUtil.out(captcha, request, response);
    }

    @GetMapping({"/register"})
    public String toRegister() {
        return "register";
    }

    @ResponseBody
    @PostMapping(value = "/register")
    public CommonResponse<Object> register(@Validated UserReq req) {
        log.info("用户注册 {}", req);
        User user = req.toEntity();
        userService.save(user);
        return CommonResponse.success("注册成功");
    }

    @Autowired
    IUserService userService;
}
