package next.controller;

import core.annotation.web.Controller;
import core.annotation.web.ExceptionHandler;
import core.annotation.web.RequestMapping;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.security.RequiredLoginException;

import javax.servlet.http.HttpServletResponse;

@Controller
public class ExceptionController extends AbstractNewController {

    @RequestMapping("/login-required")
    public ModelAndView exception() {
        throw new RequiredLoginException("Login is required!");
    }

    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView handleException(HttpServletResponse response) {
        response.addHeader("message", "handle RequiredLoginException in controller");
        return jspView("redirect:/users/loginForm");
    }
}
