package com.enjoyu.admin.web.controller;

import org.springframework.stereotype.Service;

@Service
public class TestService {
    public String getData(String id) {
        return "user:" + id;
    }
}
