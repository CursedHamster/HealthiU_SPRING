package com.example.healthiu.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    public void addViewControllers(ViewControllerRegistry registry) {
        registry.addViewController("/").setViewName("test");
        registry.addViewController("/chatroom").setViewName("chatroom");
        registry.addViewController("/chatroom/admin").setViewName("admin_chatroom");
        registry.addViewController("/chatroom/request-chatroom").setViewName("request_chatroom");
        registry.addViewController("/chatroom/user-**").setViewName("index");
        registry.addViewController("/profile").setViewName("profile");
        registry.addViewController("/profile/test").setViewName("show_test");
        registry.addViewController("/profile/tests").setViewName("show_user_test");
        registry.addViewController("/admin-register").setViewName("admin_register");
        registry.addViewController("/login").setViewName("login");
        registry.addViewController("/register").setViewName("register");
        registry.addViewController("/test").setViewName("test");
        registry.addViewController("/test/result").setViewName("test_result");
        registry.addViewController("/chat/update-message-status").setViewName("chatroom");
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/resources/**")
                .addResourceLocations("/resources/");

    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**");
    }

}