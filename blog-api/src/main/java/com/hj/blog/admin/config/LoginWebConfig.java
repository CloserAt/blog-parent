package com.hj.blog.admin.config;

import com.hj.blog.handler.LoginInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class LoginWebConfig extends WebMvcConfig {

    @Autowired
    private LoginInterceptor loginInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginInterceptor)
                .addPathPatterns("/test")
                .addPathPatterns("/comments/create/change")//此处添加评论拦截路径是因为只有用户登陆之后才可以进行评论
                .addPathPatterns("/articles/publish");
    }
}
