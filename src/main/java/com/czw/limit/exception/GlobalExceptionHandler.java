package com.czw.limit.exception;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by: czw
 * time:  2018/9/15 13:21
 */

@ControllerAdvice
public class GlobalExceptionHandler {

    @ResponseBody
    @ExceptionHandler(value = LimitException.class)
    public String defaultErrorHandle(HttpServletRequest request, Exception e) {
        return "次数过多";
    }
}
