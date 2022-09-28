package com.enjoyu.admin.controller;

import com.enjoyu.admin.components.mbp.service.IUserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@WebMvcTest(IndexController.class)
public class ContextControllerTest {
    @MockBean
    private IUserService userService;
    @Autowired
    MockMvc mockMvc;


    @Test
    void login() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/register")
                        .param("username", "admin")
                        .param("password", "123456")
                        .param("nickname", "abc")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(0))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());

    }
}
