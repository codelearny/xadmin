package com.enjoyu.admin.component.auth;

import com.enjoyu.admin.component.auth.jwt.JwtAuthenticationProvider;
import com.enjoyu.admin.component.auth.jwt.JwtClearLogoutHandler;
import com.enjoyu.admin.component.auth.jwt.JwtRefreshSuccessHandler;
import com.enjoyu.admin.component.auth.jwt.JwtTokenFilterConfigurer;
import com.enjoyu.admin.component.auth.login.RestLoginConfigurer;
import com.enjoyu.admin.component.auth.login.RestLoginFailureHandler;
import com.enjoyu.admin.component.auth.login.RestLoginSuccessHandler;
import com.enjoyu.admin.component.auth.rest.RestAccessDeniedHandler;
import com.enjoyu.admin.component.auth.rest.RestAuthenticationEntryPoint;
import com.enjoyu.admin.component.auth.rest.RestLogoutSuccessHandler;
import com.enjoyu.admin.component.auth.service.UserDetailsServiceImpl;
import com.enjoyu.admin.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.actuate.autoconfigure.security.servlet.EndpointRequest;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

/**
 * Spring Security 配置类
 *
 * @author enjoyu
 */
@Configuration
@ConditionalOnProperty(prefix = "x-admin.security", value = "jwt")
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final ObjectMapper jacksonObjectMapper;
    private final UserDetailsServiceImpl userDetailsServiceImpl;

    @Override
    public void configure(WebSecurity web) {
        //允许静态资源的无授权访问
        web.ignoring()
                .antMatchers(HttpMethod.GET, "/", "/static/**", "/favicon.ico")
                .antMatchers(HttpMethod.GET, "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //由于使用的是JWT，我们这里不需要csrf
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource()).and()
                //添加JWT filter
                .apply(new RestLoginConfigurer<>()).successHandler(restLoginSuccessHandler()).failureHandler(restLoginFailureHandler()).and()
                .apply(new JwtTokenFilterConfigurer<>()).successHandler(jwtRefreshSuccessHandler()).failureHandler(restLoginFailureHandler()).and()
                //基于token，所以不需要session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests(
                        requests -> requests
                                //允许actuator端点无授权访问
                                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                                //登录注册匿名访问
                                .antMatchers("/admin/login", "/admin/register").permitAll()
                                //跨域请求会先进行一次options请求
                                .antMatchers(HttpMethod.OPTIONS).permitAll()
                                .anyRequest().authenticated()
                )
                .logout().addLogoutHandler(jwtClearHandler()).logoutSuccessHandler(restLogoutSuccessHandler()).and()
                //添加自定义未授权和未登录结果返回
                .exceptionHandling().accessDeniedHandler(restAccessDeniedHandler()).authenticationEntryPoint(restAuthenticationEntryPoint())
        ;
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowCredentials(true);
        configuration.setAllowedOrigins(Collections.singletonList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "HEAD", "OPTION"));
        configuration.setAllowedHeaders(Collections.singletonList("*"));
        configuration.setMaxAge(Duration.ofMinutes(1));
        configuration.addExposedHeader(JwtUtils.JWT_HTTP_HEADER);
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    LogoutHandler jwtClearHandler() {
        return new JwtClearLogoutHandler();
    }

    @Bean
    LogoutSuccessHandler restLogoutSuccessHandler() {
        return new RestLogoutSuccessHandler();
    }

    @Bean
    AccessDeniedHandler restAccessDeniedHandler() {
        return new RestAccessDeniedHandler(jacksonObjectMapper);
    }

    @Bean
    JwtRefreshSuccessHandler jwtRefreshSuccessHandler() {
        return new JwtRefreshSuccessHandler();
    }

    @Bean
    RestLoginSuccessHandler restLoginSuccessHandler() {
        return new RestLoginSuccessHandler();
    }

    @Bean
    RestLoginFailureHandler restLoginFailureHandler() {
        return new RestLoginFailureHandler();
    }

    @Bean
    AuthenticationEntryPoint restAuthenticationEntryPoint() {
        return new RestAuthenticationEntryPoint(jacksonObjectMapper);
    }

    @Bean
    JwtAuthenticationProvider jwtAuthenticationProvider() {
        return new JwtAuthenticationProvider(userDetailsService());
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).and()
                .authenticationProvider(jwtAuthenticationProvider());
    }

    @Override
    protected UserDetailsService userDetailsService() {
        return userDetailsServiceImpl;
    }
}
