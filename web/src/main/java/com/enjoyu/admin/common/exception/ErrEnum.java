package com.enjoyu.admin.common.exception;

import lombok.Getter;

@Getter
public enum ErrEnum {
    ACCOUNT_NOT_FOUND(10000, "账号不存在"),
    ACCOUNT_DISABLED(10001, "账号已禁用"),
    ACCOUNT_LOCKED(10002, "账号被锁定"),
    ACCOUNT_NOT_LOGIN(10003, "账号未登录"),

    VERIFY_CODE_ERROR(10100, "验证码错误"),

    ;

    ErrEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    private final int code;
    private final String msg;
}
