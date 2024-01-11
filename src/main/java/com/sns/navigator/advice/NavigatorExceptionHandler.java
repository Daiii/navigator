package com.sns.navigator.advice;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 * freemarker统一异常处理
 *
 * @author zhangbo
 * @since 1.0.1
 */
@ControllerAdvice
public class NavigatorExceptionHandler {

    @ExceptionHandler(value = {RuntimeException.class})
    public Object exceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        ModelAndView mav = new ModelAndView();
        mav.addObject("state", "failed");
        mav.addObject("message", e.getMessage());
        mav.setViewName("/result");
        return mav;
    }
}
