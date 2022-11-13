package core.di.factory.example;

import core.annotation.web.Controller;
import core.annotation.web.RequestMapping;
import core.annotation.web.RequestMethod;
import core.mvc.JspView;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.model.User;
import next.security.LoginUser;
import next.security.RequiredLoginException;

@Controller
public class MyTestControllerV1 extends AbstractNewController {
    @RequestMapping(value = "/questions/v1", method = RequestMethod.POST)
    public ModelAndView write(@LoginUser User loginUser) {
        return jspView("/qna/form.jsp");
    }
}
