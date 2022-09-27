package core.mvc.tobe.mock;

import core.annotation.web.ControllerAdvice;
import core.annotation.web.ExceptionHandler;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.security.RequiredLoginException;

@ControllerAdvice
public class MockControllerAdvice extends AbstractNewController {

    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView requiredLogin(RequiredLoginException exception) {
        return jspView("redirect:/users/loginForm");
    }

}
