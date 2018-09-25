package com.czw.limit.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author czw
 * @date 2018/9/25 23:19
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = LimitException.class)
    public String defaultErrorHandle(HttpServletRequest request, Exception e) {
        return "次数过多";
    }
}
