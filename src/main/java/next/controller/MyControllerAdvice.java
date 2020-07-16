package next.controller;

import core.annotation.ControllerAdvice;
import core.annotation.ExceptionHandler;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.security.RequiredLoginException;

@ControllerAdvice
public class MyControllerAdvice {

    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView loginRequired() {
        return new ModelAndView(new JspView("redirect:/users/loginForm"));
    }
}
