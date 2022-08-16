package com.enjoyu.admin.controller;

import com.enjoyu.admin.controller.req.XmlReq;
import com.enjoyu.admin.controller.vo.UserVo;
import com.enjoyu.admin.controller.vo.XmlVo;
import org.assertj.core.util.Lists;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("test")
public class TestController {

    @PostMapping(value = "xml", consumes = MediaType.APPLICATION_XML_VALUE, produces = MediaType.APPLICATION_XML_VALUE)
    @ResponseBody
    public XmlVo xml(@RequestBody XmlReq req) {
        System.out.println(req);
        XmlVo vo = new XmlVo();
        vo.setDesc("test");
        List<UserVo> vos = Lists.newArrayList();
        UserVo vo1 = new UserVo();
        vo1.setId(req.getUser().getId());
        vo1.setUserName(req.getUser().getUsername());
        vos.add(vo1);
        UserVo vo2 = new UserVo();
        vo2.setId(req.getUser().getId());
        vo2.setUserName(req.getUser().getUsername());
        vos.add(vo2);
        vo.setUsers(vos);
        return vo;
    }

}
