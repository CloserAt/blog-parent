package com.hj.blog.admin.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        //跨域配置(8080访问8888也会有跨域问题):不可以为*，不安全。前后端分离项目，可能域名不一致
        //本地测试 端口不一致也算跨域
        registry.addMapping("/**").allowedOrigins("http://localhost:8080");//允许8080端口访问所有端口
    }



}
