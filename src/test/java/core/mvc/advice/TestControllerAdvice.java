package core.mvc.advice;

import core.annotation.ControllerAdvice;
import core.annotation.ExceptionHandler;
import core.mvc.ModelAndView;

@ControllerAdvice
public class TestControllerAdvice {

    @ExceptionHandler(exception = RuntimeException.class)
    public ModelAndView test() {
        return new ModelAndView().addObject("test", "testValue");
    }

}
