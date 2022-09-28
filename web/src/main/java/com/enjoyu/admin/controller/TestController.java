package com.enjoyu.admin.controller;

import com.enjoyu.admin.common.CommonResponse;
import com.enjoyu.admin.controller.req.XmlReq;
import com.enjoyu.admin.controller.vo.UserVo;
import com.enjoyu.admin.controller.vo.XmlVo;
import com.google.common.collect.Lists;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Objects;

@Controller
@RequestMapping("test")
public class TestController {

    @PostMapping(value = "xml", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public XmlVo xml(@RequestBody XmlReq req) {
        XmlVo vo = new XmlVo();
        vo.setDesc("test");
        List<UserVo> vos = Lists.newArrayList();
        UserVo vo1 = new UserVo();
        vo1.setId(req.getUser().getId());
        vo1.setUsername(req.getUser().getUsername());
        vos.add(vo1);
        UserVo vo2 = new UserVo();
        vo2.setId(req.getUser().getId());
        vo2.setUsername(req.getUser().getUsername());
        vos.add(vo2);
        vo.setUsers(vos);
        return vo;
    }

    @PostMapping(value = "session", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public CommonResponse<?> session(@RequestParam String a, HttpSession session) {
        Object b = session.getAttribute("b");
        if (Objects.equals(a, b)) {
            return CommonResponse.success("a");
        }
        return CommonResponse.error("b");
    }


}
