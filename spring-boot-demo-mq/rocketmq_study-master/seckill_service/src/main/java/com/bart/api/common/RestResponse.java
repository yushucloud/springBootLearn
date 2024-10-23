package com.bart.api.common;

import com.bart.api.enums.GlobalException;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 响应实体类
 *
 * @Author noobBart
 * @Date 2023/7/16/0016 12:22
 */
public class RestResponse<T> {
    private static final Logger log = LoggerFactory.getLogger(RestResponse.class);
    private boolean success;
    private String errorCode;
    private String errorMsg;
    private String errorDetail;
    private T data;

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

    public RestResponse() {
    }

    public RestResponse(boolean result) {
        this.success = result;
    }

    public RestResponse(boolean result, T t) {
        this.success = result;
        this.data = t;
    }

    public RestResponse(boolean result, String errorMsg, T t) {
        this.success = result;
        this.errorMsg = errorMsg;
        this.data = t;
    }

    public RestResponse(boolean result, String errorMsg) {
        this.success = result;
        if (!result) {
            this.errorMsg = errorMsg;
        }

    }

    public static <T> RestResponse<T> success(T data) {
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(true);
        restResponse.setData(data);
        return restResponse;
    }

    public static RestResponse failed(String errorCode, String errorMsg) {
        return failed(errorCode, errorMsg, errorMsg);
    }

    public static RestResponse failed(String errorMsg) {
        RestResponse restResponse = failed(GlobalException.E10008, errorMsg);
        return restResponse;
    }

    public static RestResponse failed(String errorCode, String errorMsg, String errorDetail) {
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(false);
        restResponse.setErrorCode(errorCode);
        restResponse.setErrorMsg(errorMsg);
        restResponse.setErrorDetail(errorDetail);
        return restResponse;
    }

    public static RestResponse failed(GlobalException enumExceptionCode) {
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(false);
        restResponse.setErrorMsg(enumExceptionCode.getEnglish());
        restResponse.setErrorDetail(enumExceptionCode.getChinese());
        restResponse.setErrorCode(enumExceptionCode.getCode());
        return restResponse;
    }

    public static RestResponse failed(GlobalException enumExceptionCode, String customError) {
        RestResponse restResponse = new RestResponse();
        restResponse.setSuccess(false);
        restResponse.setErrorMsg(enumExceptionCode.getErrorMsg());
        restResponse.setErrorDetail(customError);
        restResponse.setErrorCode(enumExceptionCode.getCode());
        return restResponse;
    }

    public <T> RestResponse<T> wrapIndicateTypeRestResponse(T data) {
        RestResponse<T> restResponse = new RestResponse();
        restResponse.setSuccess(this.isSuccess());
        restResponse.setErrorMsg(this.getErrorMsg());
        restResponse.setErrorCode(this.getErrorCode());
        restResponse.setData(data);
        return restResponse;
    }

    public String toString() {
        if (this.data == null || this.data instanceof BaseEntity) {
            try {
                return Json.NOT_NULL_INSTANCE.writeValueAsString(this);
//                return
//                        (new ObjectMapper()).configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false).configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false).setSerializationInclusion(JsonInclude.Include.NON_NULL).writeValueAsString(this);
            } catch (JsonProcessingException var2) {
                log.error(var2.getMessage(), var2);
            }
        }

        return "{" + "\"success\":" + this.success + ",\"errorCode\":\"" + this.errorCode + '"' + ",\"errorMsg\":\"" + this.errorMsg + '"' + ",\"errorDetail\":\"" + this.errorDetail + '"' + ",\"data\":" + this.data + '}';
    }
}
