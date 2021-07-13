package com.enjoyu.admin.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author enjoyu
 */

@Getter
@AllArgsConstructor
public enum AppErrorCode implements ErrorCode {
    /**
     * 异常码定义
     */
    INTERNAL_ERROR("500", "服务异常，请稍后再试"),
    NO_SERVICE("404", "网络异常, 服务器熔断"),
    ;


    private final String code;
    private final String desc;
}
