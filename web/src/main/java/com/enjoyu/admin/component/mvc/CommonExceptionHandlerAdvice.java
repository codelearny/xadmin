package com.enjoyu.admin.component.mvc;

import com.enjoyu.admin.web.vo.CommonResponse;
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
import java.util.Collections;
import java.util.Optional;

/**
 * 通用异常处理
 * @author enjoyu
 */
@RestControllerAdvice
public class CommonExceptionHandlerAdvice extends ResponseEntityExceptionHandler {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception.class)
    ResponseEntity<?> appException(HttpServletRequest request, Throwable ex) {
        String requestURI = request.getRequestURI();
        logger.error(requestURI + " 请求异常", ex);
        return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
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
