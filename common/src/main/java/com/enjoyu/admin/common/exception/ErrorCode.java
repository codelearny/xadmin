package com.enjoyu.admin.common.exception;

/**
 * @author enjoyu
 */
public interface ErrorCode {
    /**
     * 返回错误码
     *
     * @return 错误码
     */
    String getCode();

    /**
     * 返回错误描述
     *
     * @return 错误描述
     */
    String getDesc();

}
