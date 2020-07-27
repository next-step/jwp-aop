package core.mvc.advice;

import core.annotation.ControllerAdvice;
import core.annotation.ExceptionHandler;
import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class TestControllerAdvice {

    @ExceptionHandler(exception = RuntimeException.class)
    public ModelAndView test(HttpServletRequest request, HttpServletResponse response) {
        return new ModelAndView().addObject("test", "testValue");
    }

}
