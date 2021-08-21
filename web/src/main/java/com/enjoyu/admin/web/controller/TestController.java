package com.enjoyu.admin.web.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.beans.PropertyEditorSupport;
import java.util.Date;

@Slf4j
@Validated
@RestController
@Api(value = "my-test", tags = "测试接口")
@RequestMapping("/test")
public class TestController {
    @ApiOperation("测试接口1")
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void json(String json) {
        log.info(json);
    }

    @ApiOperation("测试接口2")
    @RequestMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public void xml(String xml) {
        log.info(xml);
    }

    @PostMapping("/hello")
    public String test() {
        return "hello";
    }

    @GetMapping("/user/{id}")
    public String serv(@PathVariable String id) {
        return testService.getData(id);
    }

    @Autowired
    TestService testService;

    @InitBinder
    public void addEditor(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) throws IllegalArgumentException {
                setValue(new Date());
            }
        });
    }
}
