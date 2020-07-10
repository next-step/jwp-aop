package next.advice;

import core.annotation.web.ControllerAdvice;
import core.annotation.web.ExceptionHandler;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.security.RequiredLoginException;

@ControllerAdvice
public class CommonControllerAdvice extends AbstractNewController {

    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView redirectToLoginPage() {

        return jspView("redirect:/users/loginPage");
    }
}
