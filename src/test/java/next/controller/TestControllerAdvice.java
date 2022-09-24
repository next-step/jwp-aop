package next.controller;

import core.annotation.web.ControllerAdvice;
import core.annotation.web.ExceptionHandler;
import core.mvc.ModelAndView;

@ControllerAdvice
public class TestControllerAdvice {

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView exception() {
        return new ModelAndView()
            .addObject("exception", "IllegalArgumentException");
    }
}
