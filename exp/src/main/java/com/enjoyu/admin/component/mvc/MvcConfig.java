package com.enjoyu.admin.component.mvc;

import com.enjoyu.admin.component.mvc.LogIntercepter;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * SpringMVC配置类
 *
 * @author enjoyu
 */
@Configuration
@ComponentScan("com.enjoyu.admin.web")
@ServletComponentScan({"com.enjoyu.admin.web.filter", "com.enjoyu.admin.web.listener"})
public class MvcConfig implements WebMvcConfigurer {

    @Autowired
    private ObjectMapper jacksonObjectMapper;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**");
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.forEach(converter -> {
            if (StringHttpMessageConverter.class.isAssignableFrom(converter.getClass())) {
                StringHttpMessageConverter cast = (StringHttpMessageConverter) converter;
                cast.setDefaultCharset(StandardCharsets.UTF_8);
            } else if (converter instanceof MappingJackson2HttpMessageConverter) {
                MappingJackson2HttpMessageConverter messageConverter = (MappingJackson2HttpMessageConverter) converter;
                messageConverter.setObjectMapper(jacksonObjectMapper);
            }
        });
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LogIntercepter()).addPathPatterns("/**");
    }
}
