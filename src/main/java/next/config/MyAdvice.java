package next.config;

import core.annotation.web.ControllerAdvice;
import core.annotation.web.ExceptionHandler;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.security.RequiredLoginException;


@ControllerAdvice
public class MyAdvice extends AbstractNewController {

    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView loginRequired(RequiredLoginException e) {
        return jspView("redirect:/users/loginForm");
    }
}