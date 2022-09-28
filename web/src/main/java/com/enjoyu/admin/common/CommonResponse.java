package com.enjoyu.admin.common;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author enjoyu
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public final class CommonResponse<T> {

    private int status;


    private String msg;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private LocalDateTime timestamp;

    private T data;

    public CommonResponse(Status status, String msg, T data) {
        this.status = status.code;
        this.msg = msg;
        this.timestamp = LocalDateTime.now();
        this.data = data;
    }

    public static <E> CommonResponse<E> success(String msg) {
        return new CommonResponse<>(Status.OK, msg, null);
    }

    public static <E> CommonResponse<E> success(String msg, E body) {
        return new CommonResponse<>(Status.OK, msg, body);
    }

    public static <E> CommonResponse<E> data(E data) {
        return success("success", data);
    }

    public static <E> CommonResponse<E> error(Throwable e) {
        return new CommonResponse<>(Status.ERROR, e.getMessage(), null);
    }

    public static <E> CommonResponse<E> error(String errorMsg) {
        return new CommonResponse<>(Status.ERROR, errorMsg, null);
    }

    enum Status {
        /**
         * 状态枚举
         */
        OK(0), ERROR(1);

        Status(int code) {
            this.code = code;
        }

        private final int code;
    }

}
