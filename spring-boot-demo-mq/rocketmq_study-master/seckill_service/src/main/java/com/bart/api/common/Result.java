package com.bart.api.common;

import com.bart.api.enums.GlobalException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 响应实体类（用这个）
 *
 * @Author noobBart
 * @Date 2023/7/17/0017 21:05
 */
public class Result<T> {
    private static final Logger log = LoggerFactory.getLogger(RestResponse.class);
    private boolean success; // 是否成功
    private String errorCode; // 失败编码
    private String errorMsg; // 失败信息
    private String errorDetail; // 失败具体信息
    private T data; // 返回体

    public boolean isSuccess() {
        return this.success;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getErrorCode() {
        return this.errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return this.errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public String getErrorDetail() {
        return this.errorDetail;
    }

    public void setErrorDetail(String errorDetail) {
        this.errorDetail = errorDetail;
    }

    public Result() {
    }

    public Result(boolean result) {
        this.success = result;
    }

    public Result(boolean result, T t) {
        this.success = result;
        this.data = t;
    }

    public Result(boolean result, String errorMsg, T t) {
        this.success = result;
        this.errorMsg = errorMsg;
        this.data = t;
    }

    public Result(boolean result, String errorMsg) {
        this.success = result;
        if (!result) {
            this.errorMsg = errorMsg;
        }

    }

    public static <T> Result<T> success(T data) {
        Result Result = new Result();
        Result.setSuccess(true);
        Result.setData(data);
        return Result;
    }

    public static Result failed(String errorCode, String errorMsg) {
        return failed(errorCode, errorMsg, errorMsg);
    }

    public static Result failed(String errorMsg) {
        Result Result = failed(GlobalException.E10008, errorMsg);
        return Result;
    }

    public static Result failed(String errorCode, String errorMsg, String errorDetail) {
        Result Result = new Result();
        Result.setSuccess(false);
        Result.setErrorCode(errorCode);
        Result.setErrorMsg(errorMsg);
        Result.setErrorDetail(errorDetail);
        return Result;
    }

    public static Result failed(GlobalException enumExceptionCode) {
        Result Result = new Result();
        Result.setSuccess(false);
        Result.setErrorMsg(enumExceptionCode.getEnglish());
        Result.setErrorDetail(enumExceptionCode.getChinese());
        Result.setErrorCode(enumExceptionCode.getCode());
        return Result;
    }

    public static Result failed(GlobalException enumExceptionCode, String customError) {
        Result Result = new Result();
        Result.setSuccess(false);
        Result.setErrorMsg(enumExceptionCode.getErrorMsg());
        Result.setErrorDetail(customError);
        Result.setErrorCode(enumExceptionCode.getCode());
        return Result;
    }

    public <T> Result<T> wrapIndicateTypeResult(T data) {
        Result<T> Result = new Result();
        Result.setSuccess(this.isSuccess());
        Result.setErrorMsg(this.getErrorMsg());
        Result.setErrorCode(this.getErrorCode());
        Result.setData(data);
        return Result;
    }

    public String toString() {
        return "{" + "\"success\":" + this.success + ",\"errorCode\":\"" + this.errorCode + '"' + ",\"errorMsg\":\"" + this.errorMsg + '"' + ",\"errorDetail\":\"" + this.errorDetail + '"' + ",\"data\":" + this.data + '}';
    }
}
