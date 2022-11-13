package next.controller;

import core.annotation.ControllerAdvice;
import core.annotation.ExceptionHandler;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.security.RequiredLoginException;

@ControllerAdvice
public class MyControllerAdvice extends AbstractNewController {
    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView loginRequired() {
        return jspView("redirect:/user/login.jsp");
    }
}
