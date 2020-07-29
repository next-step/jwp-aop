package next.config;

import core.annotation.ControllerAdvice;
import core.annotation.ExceptionHandler;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import next.security.RequiredLoginException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class MyAdvice {

    @ExceptionHandler(exception = RequiredLoginException.class)
    public ModelAndView loginRequired(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView(new JspView("redirect:/users/loginForm"));
    }

}
