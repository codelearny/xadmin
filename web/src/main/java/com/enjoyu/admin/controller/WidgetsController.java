package com.enjoyu.admin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WidgetsController {

    @GetMapping({"/widgets"})
    public String widgets() {
        return "admin/pages/widgets";
    }
}
