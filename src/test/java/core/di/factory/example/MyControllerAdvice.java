package core.di.factory.example;

import core.annotation.ControllerAdvice;
import core.annotation.ExceptionHandler;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.security.RequiredLoginException;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class MyControllerAdvice extends AbstractNewController {
    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView loginRequired(HttpServletResponse response) {
        response.setHeader("message", "Controller Advice Exception Test");
        return jspView("redirect:/user/login.jsp");
    }
}
