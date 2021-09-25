package com.restaurantreservation.config;

import com.restaurantreservation.config.interceptor.LoginInterceptor;
import com.restaurantreservation.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@RequiredArgsConstructor
public class InterceptorConfig implements WebMvcConfigurer {

    private final UserService userService;

    // login-check 에서만 확인하도록 일단 설정
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new LoginInterceptor(userService))
                .order(1)
                .addPathPatterns("/login-check")
                .excludePathPatterns("/", "/user/login", "/user/logout")
        ;

        WebMvcConfigurer.super.addInterceptors(registry);
    }
}
