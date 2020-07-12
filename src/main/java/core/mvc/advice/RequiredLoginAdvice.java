package core.mvc.advice;

import core.annotation.web.ControllerAdvice;
import core.annotation.web.ExceptionHandler;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.security.RequiredLoginException;

@ControllerAdvice
public class RequiredLoginAdvice {
    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView loginRequired() {
        return new ModelAndView(new JspView("redirect:/users/loginForm"));
    }
}
