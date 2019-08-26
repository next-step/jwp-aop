package core.di.factory.example;

import core.annotation.Inject;
import core.annotation.web.Controller;

@Controller
public class MyUserController {
    @Inject
    private MyQnaService qnaService;

    private MyUserService userService;

    @Inject
    public void setUserService(MyUserService userService) {
        this.userService = userService;
    }

    public MyUserService getUserService() {
        return userService;
    }
}
