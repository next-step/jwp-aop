package next.controller;

import javax.servlet.http.HttpServletResponse;

import core.annotation.web.Controller;
import core.annotation.web.ExceptionHandler;
import core.annotation.web.RequestMapping;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.model.User;
import next.security.LoginUser;
import next.security.RequiredLoginException;

@Controller
public class ExceptionController extends AbstractNewController {

    @RequestMapping("/login-required")
    public ModelAndView exception(@LoginUser User loginUser) {
        return jspView("redirect:/");
    }

    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView handleException(HttpServletResponse response) {
        response.addHeader("message", "handle RequiredLoginException in controller");
        return jspView("redirect:/users/loginForm");
    }
}
