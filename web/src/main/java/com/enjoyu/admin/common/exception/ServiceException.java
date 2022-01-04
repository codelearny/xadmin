package com.enjoyu.admin.common.exception;

/**
 * @author enjoyu
 */
public class ServiceException extends RuntimeException {

    private final ErrEnum errEnum;

    public ServiceException(ErrEnum errEnum) {
        super(errEnum.getMsg());
        this.errEnum = errEnum;
    }

    public ErrEnum getErrEnum() {
        return errEnum;
    }

}
