package next.controller;

import core.annotation.web.ControllerAdvice;
import core.annotation.web.ExceptionHandler;
import core.mvc.ModelAndView;

@ControllerAdvice
public class TestControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView illegalArgumentException() {
        return new ModelAndView()
            .addObject("exception", "IllegalArgumentException in ControllerAdvice");
    }

    @ExceptionHandler(IllegalStateException.class)
    public ModelAndView illegalStateException() {
        return new ModelAndView()
            .addObject("exception", "IllegalStateException in ControllerAdvice");
    }
}
