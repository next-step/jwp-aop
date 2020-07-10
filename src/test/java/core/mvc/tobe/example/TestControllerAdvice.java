package core.mvc.tobe.example;

import core.annotation.web.ControllerAdvice;
import core.annotation.web.ExceptionHandler;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.security.RequiredLoginException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class TestControllerAdvice extends AbstractNewController {

    @ExceptionHandler(value = {IllegalArgumentException.class, NumberFormatException.class})
    public ModelAndView test(HttpServletRequest request) {
        return jspView("redirect:/users/loginForm");
    }

    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView withOneExceptionClass(HttpServletResponse request) {
        return jspView("redirect:/users/loginForm");
    }
}
