package com.enjoyu.admin.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/ft")
public class FtController {
    @RequestMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void json(String json) {
        log.info(json);
    }

    @RequestMapping(consumes = MediaType.APPLICATION_XML_VALUE)
    public void xml(String xml) {
        log.info(xml);
    }

    @PostMapping("/test")
    public void test(){

    }
}
