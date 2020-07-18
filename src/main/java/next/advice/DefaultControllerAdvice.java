package next.advice;

import core.annotation.web.ControllerAdvice;
import core.annotation.web.ExceptionHandler;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.security.RequiredLoginException;

/**
 * @author KingCjy
 */
@ControllerAdvice
public class DefaultControllerAdvice {
    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView requireLoginException(RequiredLoginException e) {
        return new ModelAndView(new JspView("redirect:/users/loginForm"));
    }
}
