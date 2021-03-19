package com.enjoyu.admin.common.exception;

public class ServiceException extends BaseException {

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String format, Object... args) {
        super(String.format(format, args));
    }

    public ServiceException(Throwable cause) {
        super(cause);
    }

    public ServiceException(Throwable cause, String message) {
        super(message, cause);
    }

    public ServiceException(Throwable cause, String format, Object... args) {
        super(String.format(format, args), cause);
    }
}
