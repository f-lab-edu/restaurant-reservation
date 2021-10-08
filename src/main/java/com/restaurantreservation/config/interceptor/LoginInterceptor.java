package com.restaurantreservation.config.interceptor;

import com.restaurantreservation.domain.user.login.JwtType;
import com.restaurantreservation.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Slf4j
@RequiredArgsConstructor
public class LoginInterceptor implements HandlerInterceptor {

    private final UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("loginInterceptor 실행 {}", request);

        String accessToken = request.getHeader(String.valueOf(JwtType.ACCESS_TOKEN));
        if (accessToken != null) {
            userService.checkAccessToken(accessToken);
        }

        return true;
    }
}
