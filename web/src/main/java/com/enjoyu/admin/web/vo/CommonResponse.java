package com.enjoyu.admin.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class CommonResponse<T> {

    private int status = 1;

    private String errorCode = "";

    private String errorMsg = "";

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private T resultBody;

    public CommonResponse(T resultBody) {
        this.resultBody = resultBody;
    }

    public CommonResponse(int status, String errorCode, String errorMsg, T resultBody) {
        this.status = status;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.timestamp = LocalDateTime.now();
        this.resultBody = resultBody;
    }

    public static <E> CommonResponse<E> success() {
        return new CommonResponse<>(HttpStatus.OK.value(), "success", "请求成功", null);
    }

    public static <E> CommonResponse<E> success(E body) {
        return new CommonResponse<>(HttpStatus.OK.value(), "success", "请求成功", body);
    }

    public static <E> CommonResponse<E> error(Exception e) {
        return new CommonResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "error", e.getMessage(), null);
    }

    public static <E> CommonResponse<E> error(HttpStatus status, Exception e) {
        return new CommonResponse<>(status.value(), "error", e.getMessage(), null);
    }

    public static <E> CommonResponse<E> error(HttpStatus status, String errorCode, String errorMsg) {
        return new CommonResponse<>(status.value(), errorCode, errorMsg, null);
    }

    public static <E> CommonResponse<E> forbidden(String errorMsg) {
        return new CommonResponse<>(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(), errorMsg, null);
    }

    public static <E> CommonResponse<E> unauthorized(String errorMsg) {
        return new CommonResponse<>(HttpStatus.UNAUTHORIZED.value(), HttpStatus.UNAUTHORIZED.getReasonPhrase(), errorMsg, null);
    }

}
