package com.bart.api.common;

import com.bart.api.enums.GlobalException;

/**
 * 异常处理类
 * @Author noobBart
 * @Date 2023/7/17/0017 21:15
 */
public class CastException extends RuntimeException {
    private String errorCode;

    public CastException() {
    }

    public CastException(GlobalException message) {
        super(message.getErrorMsg());
        this.errorCode = message.getCode();
    }

    public CastException(String message) {
        super(message);
    }

    public CastException(String message, Throwable cause) {
        super(message, cause);
    }

    public CastException(Throwable cause) {
        super(cause);
    }

    protected CastException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
