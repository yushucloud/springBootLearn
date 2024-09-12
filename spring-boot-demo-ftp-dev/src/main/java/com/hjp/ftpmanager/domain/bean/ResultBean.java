package com.hjp.ftpmanager.domain.bean;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * @program: ftp-manager
 * @description: 通用resultBean返回
 * @author: jumper Hou
 * @create: 2022/9/8 17:08
 **/
@Getter
@Setter
@Accessors(chain = true)
public class ResultBean<T> {
    private Integer code;
    private String message;
    private T data;

    public static <T> ResultBean<T> success() {
        return new ResultBean<T>().setCode(Status.SUCCESS.code).setMessage(Status.SUCCESS.status);
    }

    public static <T> ResultBean<T> success(T data) {
        return new ResultBean<T>().setCode(Status.SUCCESS.code).setMessage(Status.SUCCESS.status).setData(data);
    }

    public static <T> ResultBean<T> success(String msg) {
        return new ResultBean<T>().setCode(Status.SUCCESS.code).setMessage(msg);
    }

    public static <T> ResultBean<T> success(String msg, T data) {
        return new ResultBean<T>().setCode(Status.SUCCESS.code).setMessage(msg).setData(data);
    }

    public static <T> ResultBean<T> fail() {
        return new ResultBean<T>().setCode(Status.FAIL.code);
    }

    public static <T> ResultBean<T> fail(String msg) {
        return new ResultBean<T>().setCode(Status.FAIL.code).setMessage(msg);
    }


    @Getter
    @AllArgsConstructor
    public enum Status {
        /**
         * 成功
         */
        SUCCESS(0, "SUCCESS"),
        /**
         * 失败
         */
        FAIL(1, "FAIL");

        private final Integer code;
        private final String status;
    }
}
