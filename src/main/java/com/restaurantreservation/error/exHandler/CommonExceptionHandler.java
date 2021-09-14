package com.restaurantreservation.error.exHandler;

import com.restaurantreservation.controller.Result;
import com.restaurantreservation.error.exception.user.UserException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * exception 처리를 위한 공통
 * controllerAdvice 구현
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    /**
     * Exception 에서 필수 메세지들을 표시하는 느낌으로,
     * BaseExceptionResponse 에 인자로 넣어 두고
     * BaseExceptionResponse 을 extends 하도록 구현함으로써
     * BaseExceptionResponse 를 implement 한 객체들만 Return 될 수 있도록 하였습니다.
     */
//    @ResponseBody
//    @ExceptionHandler(UserException.class)
//    public Result<UserException> userException(UserException e) {
//        return Result.createErrorResult(
//                e.getUserExceptionMessage().getCode(),
//                e.getLocalizedMessage()
//        );
//    }

    /**
     * 예상범위의 exception 처리를 못하고 RuntimeException 까지 넘어갔을 때,
     * 개발자들이 확인 할 수 있게, log 를 찍고 사용자에게는 간단한 메세지만 넘겨주는 것 처리
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Result<RuntimeException> runtimeException(RuntimeException e) {
        log.error("[runtime exception 내용 ={}]", e.getMessage());
        return Result.createErrorResult(600, e.getLocalizedMessage());
    }

    //최상위
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public Result<Error> exHandler(Exception e) {
        log.error("[error ex] = {}", e.getLocalizedMessage());
        return Result.createErrorResult(500, "내부 오류");
    }


}
