package core.di.factory.example;

import core.annotation.web.Controller;
import core.annotation.web.RequestMapping;
import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class ControllerAdviceTestController {

    @RequestMapping("/test/controllerAdvice/firstSampleException")
    public ModelAndView firstSampleException(HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new FirstSampleException();
    }

    @RequestMapping("/test/controllerAdvice/secondSampleException")
    public ModelAndView secondSampleException(HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new SecondSampleException();
    }

    @RequestMapping("/test/controllerAdvice/thirdSampleException")
    public ModelAndView thirdSampleException(HttpServletRequest request, HttpServletResponse response) throws Exception {
        throw new ThirdSampleException();
    }
}
