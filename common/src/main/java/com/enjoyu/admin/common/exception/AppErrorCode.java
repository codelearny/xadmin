package com.enjoyu.admin.common.exception;

/**
 * @author enjoyu
 */

public enum AppErrorCode implements ErrorCode {
    /**
     * 异常码定义
     */
    INTERNAL_ERROR("500", "服务异常，请稍后再试"),
    NO_SERVICE("404", "网络异常, 服务器熔断"),
    ;

    AppErrorCode(String code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    @Override
    public String getCode() {
        return code;
    }

    @Override
    public String getDesc() {
        return desc;
    }

    private final String code;
    private final String desc;
}
