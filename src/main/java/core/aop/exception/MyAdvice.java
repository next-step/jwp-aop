package core.aop.exception;

import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.security.RequiredLoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class MyAdvice extends AbstractNewController {

    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView loginRequired(Exception exception) {
        return jspView("redirect:/users/loginForm");
    }

}
