package core.di.factory.example;

import core.annotation.ControllerAdvice;
import core.annotation.ExceptionHandler;
import core.mvc.JsonView;
import core.mvc.ModelAndView;

@ControllerAdvice
public class TestControllerAdvice {

    @ExceptionHandler(FirstSampleException.class)
    public ModelAndView handleFirstSampleException(FirstSampleException e) {
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("error", e.getClass().getSimpleName());
        return mav;
    }

    @ExceptionHandler({SecondSampleException.class, ThirdSampleException.class})
    public ModelAndView handleMultipleSampleExceptions(Exception e) {
        ModelAndView mav = new ModelAndView(new JsonView());
        mav.addObject("error", e.getClass().getSimpleName());
        return mav;
    }
}
