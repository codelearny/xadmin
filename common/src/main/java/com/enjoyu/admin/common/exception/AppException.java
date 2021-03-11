package com.enjoyu.admin.common.exception;

public class AppException extends BaseException {
    private final AppErrorCode appErrorCode;

    public AppException(AppErrorCode appErrorCode) {
        super(appErrorCode.getDesc());
        this.appErrorCode = appErrorCode;
    }

    public String getCode() {
        return appErrorCode.getCode();
    }
}
