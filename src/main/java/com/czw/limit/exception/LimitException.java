package com.czw.limit.exception;

/**
 * @author czw
 * @date 2018/9/25 23:19
 */
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
