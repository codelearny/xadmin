package com.enjoyu.admin.common.exception;

/**
 * 应用异常
 *
 * @author enjoyu
 */
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
