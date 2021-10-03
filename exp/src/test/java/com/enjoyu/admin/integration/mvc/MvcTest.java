package com.enjoyu.admin.integration.mvc;


import com.enjoyu.admin.web.controller.TestController;
import com.enjoyu.admin.web.controller.TestService;
import com.enjoyu.admin.web.filter.RequestLogFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringJUnitWebConfig({TestController.class})
public class MvcTest {
    MockMvc mockMvc;
    @MockBean
    TestService testService;

    @BeforeEach
    void setup(WebApplicationContext wac) {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .addFilters(new RequestLogFilter())
                .build();
    }

    @Test
    public void test() throws Exception {
        when(testService.getData(Mockito.anyString())).thenReturn("00000000000000000000");
        this.mockMvc
                .perform(get("/test/user/{id}", "123"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string("00000000000000000000"));
    }

}
