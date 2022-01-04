package com.enjoyu.admin.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.SharedHttpSessionConfigurer.sharedHttpSession;

/**
 * @author enjoyu
 */
public class SingleControllerTest {
    MockMvc mockMvc;

    @BeforeEach
    void setup() {
        this.mockMvc = MockMvcBuilders.standaloneSetup(new IndexController()).apply(sharedHttpSession()).build();
    }

    @Test
    public void test1() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(post("/login")
                        .param("userName", "admin")
                        .param("password", "123456")
                        .param("verifyCode", "12")
                        .sessionAttr("captcha","12")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isOk())
                .andReturn();
    }

}
