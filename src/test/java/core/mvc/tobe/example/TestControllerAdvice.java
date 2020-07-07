package core.mvc.tobe.example;

import core.annotation.web.ControllerAdvice;
import core.annotation.web.ExceptionHandler;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class TestControllerAdvice extends AbstractNewController {

    @ExceptionHandler(value = {IllegalArgumentException.class})
    public ModelAndView test(HttpServletRequest request) {
        return jspView("redirect:/users/loginForm");
    }
}
