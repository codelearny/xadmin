package com.enjoyu.admin.component.mvc;

import com.enjoyu.admin.web.vo.CommonResponse;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.Optional;

import static com.enjoyu.admin.common.constant.DateFormatConstant.DEFAULT_DATETIME_PATTERN;

/**
 * 通用异常处理
 *
 * @author enjoyu
 */
@RestControllerAdvice
public class CommonExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DEFAULT_DATETIME_PATTERN);
        binder.registerCustomEditor(Date.class, new CustomDateEditor(dateFormat, true));
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    CommonResponse<?> appException(HttpServletRequest request, Throwable ex) {
        String requestURI = request.getRequestURI();
        logger.error(requestURI + " 请求异常", ex);
        return CommonResponse.error(ex);
    }

    /**
     * 方法参数校验异常
     */
    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public CommonResponse<Object> argError(MethodArgumentNotValidException exception) {
        FieldError fieldError = exception.getBindingResult().getFieldError();
        Optional.of(exception)
                .map(MethodArgumentNotValidException::getBindingResult)
                .map(Errors::getAllErrors)
                .orElseGet(Collections::emptyList)
                .forEach(logger::error);
        return CommonResponse.error(exception);
    }
}
