package core.di.factory.example;

import core.annotation.ExceptionHandler;
import core.annotation.web.Controller;
import core.annotation.web.RequestMapping;
import core.annotation.web.RequestMethod;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.model.User;
import next.security.LoginUser;
import next.security.RequiredLoginException;

import javax.servlet.http.HttpServletResponse;

@Controller
public class MyTestControllerV2 extends AbstractNewController {
    @RequestMapping(value = "/questions/v2", method = RequestMethod.POST)
    public ModelAndView write(@LoginUser User loginUser) {
        return jspView("/qna/form.jsp");
    }


    @ExceptionHandler(RequiredLoginException.class)
    public ModelAndView loginRequired(HttpServletResponse response) {
        response.setHeader("message", "ExceptionHandler Test in Specific Controller");
        return jspView("redirect:/user/login.jsp");
    }
}
