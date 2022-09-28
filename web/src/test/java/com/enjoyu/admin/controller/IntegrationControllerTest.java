package com.enjoyu.admin.controller;

import com.enjoyu.admin.components.mbp.entity.User;
import com.enjoyu.admin.repository.SysUserRepository;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.apache.shiro.web.subject.WebSubject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
public class IntegrationControllerTest {
    MockMvc mockMvc;

    @MockBean
    private SysUserRepository userRepository;
    @Autowired
    private SecurityManager securityManager;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    private SessionDAO sessionDAO;
    private MockHttpServletRequest mockHttpServletRequest;
    private MockHttpServletResponse mockHttpServletResponse;

    @BeforeEach
    void setUp() {
        mockHttpServletRequest = new MockHttpServletRequest(webApplicationContext.getServletContext());
        mockHttpServletResponse = new MockHttpServletResponse();
        MockHttpSession mockHttpSession = new MockHttpSession(webApplicationContext.getServletContext());
        mockHttpServletRequest.setSession(mockHttpSession);
        SecurityUtils.setSecurityManager(securityManager);
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    void login() throws Exception {
        mockUser();
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.post("/login")
                        .param("username", "admin")
                        .param("password", "321321")
                        .param("verifyCode", "12")
                        .sessionAttr("captcha", "12")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(0))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
        System.out.println("all session id:" +
                sessionDAO.getActiveSessions().stream()
                        .map(Session::getId)
                        .reduce((x, y) -> x + "," + y)
                        .orElse(""));

    }

    @Test
    void users() throws Exception {
        mockUser();
        mockLogin();
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders.get("/admin/user")
                        .param("pageIndex", "1")
                        .param("pageSize", "10")
                        .accept(MediaType.APPLICATION_JSON))

                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value(0))
                .andDo(MockMvcResultHandlers.print())
                .andReturn();

        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    private void mockUser() {
        User user = new User();
        user.setUsername("admin");
        user.setPassword("1xBljjuPJZKk9aj79Onl5OtyA4RuwFBVmgDDuuhXyMM=");
        user.setSalt("MYdzi36UIcyVBPYwsfcefw==");
        user.setStatus(true);
        Mockito.when(userRepository.user(any())).thenReturn(user);
    }

    private void mockLogin() {
        Subject subject = new WebSubject.Builder(mockHttpServletRequest, mockHttpServletResponse).buildWebSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("admin", "321321", true);
        subject.login(token);
        ThreadContext.bind(subject);
    }

}
