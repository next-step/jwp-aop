package next.controller;

import core.annotation.web.Controller;
import core.annotation.web.ExceptionHandler;
import core.mvc.ModelAndView;

@Controller
public class ExceptionHandlerController {

    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView illegalArgumentException() {
        return new ModelAndView()
            .addObject("exception", "IllegalArgumentException in Controller");
    }
}
