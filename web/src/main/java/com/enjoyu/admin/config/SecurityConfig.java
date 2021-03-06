package com.enjoyu.admin.config;

import com.enjoyu.admin.component.auth.RestAccessDeniedHandler;
import com.enjoyu.admin.component.auth.RestAuthenticationEntryPoint;
import com.enjoyu.admin.component.auth.RestLogoutSuccessHandler;
import com.enjoyu.admin.component.auth.UserDetailsServiceImpl;
import com.enjoyu.admin.component.auth.jwt.JwtAuthenticationProvider;
import com.enjoyu.admin.component.auth.jwt.JwtRefreshSuccessHandler;
import com.enjoyu.admin.component.auth.jwt.JwtTokenFilterConfigurer;
import com.enjoyu.admin.component.auth.login.RestLoginConfigurer;
import com.enjoyu.admin.component.auth.login.RestLoginFailureHandler;
import com.enjoyu.admin.component.auth.login.RestLoginSuccessHandler;
import com.enjoyu.admin.utils.JwtUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;

/**
 * Spring Security ?????????
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
        //????????????????????????????????????
        web.ignoring()
                .antMatchers(HttpMethod.GET, "/", "/static/**", "/favicon.ico")
                .antMatchers(HttpMethod.GET, "/swagger-ui/**", "/swagger-resources/**", "/v2/api-docs/**");
    }

    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                //??????????????????JWT????????????????????????csrf
                .csrf().disable()
                .cors().configurationSource(corsConfigurationSource()).and()
                //??????JWT filter
                .apply(new RestLoginConfigurer<>()).successHandler(restLoginSuccessHandler()).failureHandler(restLoginFailureHandler()).and()
                .apply(new JwtTokenFilterConfigurer<>()).successHandler(jwtRefreshSuccessHandler()).failureHandler(restLoginFailureHandler()).and()
                //??????token??????????????????session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()
                .authorizeRequests(
                        requests -> requests
                                //??????actuator?????????????????????
                                .requestMatchers(EndpointRequest.toAnyEndpoint()).permitAll()
                                //????????????????????????
                                .antMatchers("/admin/login", "/admin/register").permitAll()
                                //??????????????????????????????options??????
                                .antMatchers(HttpMethod.OPTIONS).permitAll()
                                .anyRequest().authenticated()
                )
                .logout().logoutSuccessHandler(restLogoutSuccessHandler()).and()
                //????????????????????????????????????????????????
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
