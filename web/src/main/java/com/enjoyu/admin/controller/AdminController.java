package com.enjoyu.admin.controller;

import com.enjoyu.admin.common.CommonResponse;
import com.enjoyu.admin.common.ServiceException;
import com.wf.captcha.ArithmeticCaptcha;
import com.wf.captcha.utils.CaptchaUtil;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotBlank;
import java.io.IOException;

import static com.enjoyu.admin.common.Constants.LONGIN_USER;

@Controller
public class AdminController {

    @GetMapping({"/login"})
    public String login() {
        return "login";
    }

    @GetMapping({"", "/", "/index", "/index.html"})
    public String index(HttpServletRequest request) {
        request.setAttribute("path", "index");
        return "index";
    }

    @ResponseBody
    @PostMapping(value = "/login")
    public CommonResponse<Object> login(@NotBlank String userName, @NotBlank String password, @NotBlank String verifyCode, HttpServletRequest request, HttpSession session) {
        if (!CaptchaUtil.ver(verifyCode, request)) {
            throw new ServiceException("验证码错误");
        }
        String adminUser = "";
        session.setAttribute(LONGIN_USER, adminUser);
        return CommonResponse.success("登录成功");
    }


    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ArithmeticCaptcha captcha = new ArithmeticCaptcha(130, 48, 2);
        CaptchaUtil.out(captcha, request, response);
    }


    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.removeAttribute(LONGIN_USER);
        return "redirect:/login";
    }
}
