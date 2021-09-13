package com.restaurantreservation.error.exHandler;

import com.restaurantreservation.error.exception.user.UserException;
import com.restaurantreservation.response.BaseExceptionResponse;
import com.restaurantreservation.response.RuntimeExceptionResponse;
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
        // 알 수 없는 에러는 UserException 에서 잡히지 않아서, RuntimeException 에서 처리 하는것으로 변경
//        log.error("[userJoinExHandler ex = {}", e.getUserExceptionMessage());
        //알 수 없는 오류일 때만 서버단의 http code 를 500 error 로 리턴
//        if (e.getUserExceptionMessage().getHttpCode() == 500) {
//            return ResponseEntity.internalServerError().body(UserExceptionResponse.of(e.getUserExceptionMessage()));
//        }
        return ResponseEntity.ok(UserExceptionResponse.of(e.getUserExceptionMessage()));
    }

    /**
     * 예상범위의 exception 처리를 못하고 RuntimeException 까지 넘어갔을 때,
     * 개발자들이 확인 할 수 있게, log 를 찍고 사용자에게는 간단한 메세지만 넘겨주는 것 처리
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<? extends BaseExceptionResponse> runtimeException(RuntimeException e){
        log.error("[runtime exception 내용 ={}", e.getMessage());
        return ResponseEntity.internalServerError().body(RuntimeExceptionResponse.of(e));
    }



}
