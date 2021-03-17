package com.enjoyu.admin.config;

import com.enjoyu.admin.auth.JwtAuthenticationTokenFilter;
import com.enjoyu.admin.auth.RestAccessDeniedHandler;
import com.enjoyu.admin.auth.RestAuthenticationEntryPoint;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.actuate.health.HealthEndpoint;
import org.springframework.boot.actuate.info.InfoEndpoint;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@ConditionalOnProperty(prefix = "xadmin.security", value = "jwt")
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private RestAccessDeniedHandler restAccessDeniedHandler;
    private RestAuthenticationEntryPoint restAuthenticationEntryPoint;


    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                // 由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                // cors
                .cors().and()
                // 添加JWT filter
                .addFilterBefore(jwtAuthenticationTokenFilter(), UsernamePasswordAuthenticationFilter.class)
                // 基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests(
                        requests -> requests
                                // 允许对于网站静态资源的无授权访问
                                .antMatchers(HttpMethod.GET, "/", "/static/**", "/favicon.ico", "/swagger-resources/**", "/v2/api-docs/**").permitAll()
                                // 允许actuator部分端点无授权访问
                                .requestMatchers(EndpointRequest.to(HealthEndpoint.class, InfoEndpoint.class)).permitAll()
                                // 对登录注册要允许匿名访问
                                .antMatchers("/admin/login", "/admin/register").permitAll()
                                //跨域请求会先进行一次options请求
                                .antMatchers(HttpMethod.OPTIONS).permitAll()
                                // 除上面外的所有请求全部需要鉴权认证
                                .anyRequest().authenticated()
                )
                .logout().and()
                //添加自定义未授权和未登录结果返回
                .exceptionHandling().accessDeniedHandler(restAccessDeniedHandler).authenticationEntryPoint(restAuthenticationEntryPoint)
        ;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }

    @Bean
    public JwtAuthenticationTokenFilter jwtAuthenticationTokenFilter() {
        return new JwtAuthenticationTokenFilter();
    }

}
