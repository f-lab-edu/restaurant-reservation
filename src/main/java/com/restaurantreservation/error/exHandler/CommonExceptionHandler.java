package com.restaurantreservation.error.exHandler;

import com.restaurantreservation.error.exception.user.UserException;
import com.restaurantreservation.response.BaseExceptionResponse;
import com.restaurantreservation.response.user.UserExceptionResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * exception 처리를 위한 공통
 * controllerAdvice 구현
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ResponseBody
    @ExceptionHandler(UserException.class)
    public ResponseEntity<? extends BaseExceptionResponse> userJoinException(UserException e) {
        //확인용으로 찍는 것 - 만약 서버 배포를 한다면 알 수 없는 오류 일 때만 기록하도록 수정 필요
        log.error("[userJoinExHandler ex = {}", e.getUserExceptionMessage());
        //알 수 없는 오류일 때만 서버단의 http code 를 500 error 로 리턴
        if (e.getUserExceptionMessage().getHttpCode() == 500) {
            return ResponseEntity.internalServerError().body(UserExceptionResponse.of(e.getUserExceptionMessage()));
        }
        return ResponseEntity.ok(UserExceptionResponse.of(e.getUserExceptionMessage()));
    }

}
