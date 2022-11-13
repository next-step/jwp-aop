package next.config;

import core.annotation.web.ControllerAdvice;
import core.annotation.web.ExceptionHandler;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.security.RequiredLoginException;

@ControllerAdvice
public class MyControllerAdvice extends AbstractNewController {
    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView handleRequiredLoginException(RequiredLoginException exception) {
        return jspView("redirect:/users/loginForm");
    }
}
