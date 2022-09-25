package next.controller;

import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.security.RequiredLoginException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class UserControllerAdvice {

    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView numberFormatException() {
        return new ModelAndView(new JspView("redirect:/users/loginForm"));
    }
}
