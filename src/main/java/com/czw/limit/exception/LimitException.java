package com.czw.limit.exception;


public class LimitException extends RuntimeException {


    /**
     * 自定义错误信息
     *
     * @param message
     */
    public LimitException(String message) {
        super(message);
    }

}
