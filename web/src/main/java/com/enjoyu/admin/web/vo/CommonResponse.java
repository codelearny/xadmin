package com.enjoyu.admin.web.vo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

import static com.enjoyu.admin.web.vo.CommonResponse.Status.ERROR;
import static com.enjoyu.admin.web.vo.CommonResponse.Status.OK;

/**
 * @author enjoyu
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class CommonResponse<T> {

    private int status;

    private String errorCode;

    private String errorMsg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime timestamp;

    private T data;

    public CommonResponse(Status status, String errorCode, String errorMsg, T data) {
        this.status = status.code;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }

    public static <E> CommonResponse<E> success() {
        return new CommonResponse<>(OK, "success", "请求成功", null);
    }

    public static <E> CommonResponse<E> success(E body) {
        return new CommonResponse<>(OK, "success", "请求成功", body);
    }

    public static <E> CommonResponse<E> error(Throwable e) {
        return new CommonResponse<>(ERROR, "error", e.getMessage(), null);
    }

    public static <E> CommonResponse<E> error(String errorCode, String errorMsg) {
        return new CommonResponse<>(ERROR, errorCode, errorMsg, null);
    }

    enum Status {
        OK(0), ERROR(1);

        Status(int code) {
            this.code = code;
        }

        private final int code;
    }

}
