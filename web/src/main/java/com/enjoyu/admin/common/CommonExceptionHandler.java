package com.enjoyu.admin.common;

import com.enjoyu.admin.common.exception.ServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.PrintWriter;
import java.io.StringWriter;

/**
 * @author enjoyu
 */
@Slf4j
@RestControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(ServiceException.class)
    public Object serviceException(ServiceException e) {
        log.error(e.getMessage(), e);
        return CommonResponse.error(e);
    }

    @ExceptionHandler(Exception.class)
    public Object allException(Exception e, HttpServletRequest req) {
        log.error(e.getMessage(), e);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("path", req.getRequestURI());
        StringWriter stackTrace = new StringWriter();
        e.printStackTrace(new PrintWriter(stackTrace));
        stackTrace.flush();
        modelAndView.addObject("trace", stackTrace.toString());
        modelAndView.setViewName("error");
        return modelAndView;
    }
}
