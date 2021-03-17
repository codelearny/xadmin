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
}
