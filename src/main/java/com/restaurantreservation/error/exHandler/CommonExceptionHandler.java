package com.restaurantreservation.error.exHandler;

import com.restaurantreservation.controller.Result;
import com.restaurantreservation.error.exception.BaseException;
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
     * custom 한 exception 들은
     * RuntimeException 을 상속한 BaseException 을
     * 상속하면서 만들고, 각각 exception 상황에 맞는 message 들은
     * BaseExceptionMessage 들을 상속해서 이루어 지도록 구현되어 있습니다.
     *
     * 그래서 UserException(UserExceptionMessage.USER_NOT_FOUNT);
     * 를 호출 할 시에도 아래 ExceptionHandler 에서 잡아서 각 상황에 맞는 코드와, 메세지를 보낼 수 있습니다.
     *
     * 또한, custom exception 을 처리 하지 못(안) 한 exception 이 터질 경우 RuntimeException 안에 속한 에러이면
     * 아래 ExceptionHandler 에서 잡게 됩니다.
     */
    @ResponseBody
    @ExceptionHandler(RuntimeException.class)
    public Result<RuntimeException> runtimeException(RuntimeException e) {
        log.error("[runtime exception 내용 ={}]", e.getMessage());
        if (e instanceof BaseException) {
            BaseException baseException = (BaseException) e;
            return Result.createErrorResult(baseException.getBaseExceptionMessage().getCode(), baseException.getLocalizedMessage());
        }
        return Result.createErrorResult(500, e.getLocalizedMessage());
    }

    //최상위
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public Result<Error> exHandler(Exception e) {
        log.error("[error ex] = {}]", e.getLocalizedMessage());
        return Result.createErrorResult(500, "내부 오류");
    }


}
