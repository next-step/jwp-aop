package next.controller;

import core.annotation.ControllerAdvice;
import core.annotation.ExceptionHandler;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.security.RequiredLoginException;

@ControllerAdvice
public class GlobalExceptionHandler extends AbstractNewController {

    @ExceptionHandler(exception = RequiredLoginException.class)
    public ModelAndView exceptionHandle(Exception e) {
        return jspView("redirect:/users/loginForm");
    }
}
