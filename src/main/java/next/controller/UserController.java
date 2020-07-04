package next.controller;

import core.annotation.Inject;
import core.annotation.web.*;
import core.mvc.ModelAndView;
import core.mvc.tobe.AbstractNewController;
import next.dao.UserDao;
import next.dto.UserCreatedDto;
import next.dto.UserUpdatedDto;
import next.model.User;
import next.security.LoginUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
public class UserController extends AbstractNewController {
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    private UserDao userDao;

    @Inject
    public UserController(UserDao userDao) {
        this.userDao = userDao;
    }

    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public ModelAndView list(@LoginUser User loginUser) throws Exception {
        ModelAndView mav = jspView("/user/list.jsp");
        mav.addObject("users", userDao.findAll());
        return mav;
    }

    @RequestMapping(value = "/users/profile", method = RequestMethod.GET)
    public ModelAndView profile(@RequestParam String userId) throws Exception {
        log.debug("profile userId : {}", userId);

        ModelAndView mav = jspView("/user/profile.jsp");
        User user = userDao.findByUserId(userId);
        log.debug("profile user : {}", user);
        mav.addObject("user", user);
        return mav;
    }

    @RequestMapping(value = "/users/form", method = RequestMethod.GET)
    public ModelAndView form() throws Exception {
        return jspView("/user/form.jsp");
    }

    @RequestMapping(value = "/users/{userId}/form", method = RequestMethod.GET)
    public ModelAndView updateForm(@LoginUser User loginUser, @PathVariable String userId) throws Exception {
        if (!loginUser.isSameUser(userId)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }
        ModelAndView mav = jspView("/user/updateForm.jsp");
        mav.addObject("user", loginUser);
        return mav;
    }

    @RequestMapping(value = "/users/{userId}", method = RequestMethod.POST)
    public ModelAndView update(@LoginUser User loginUser,
                               @PathVariable String userId,
                               UserUpdatedDto updateUser) throws Exception {
        User user = userDao.findByUserId(updateUser.getUserId());
        if (!loginUser.isSameUser(user)) {
            throw new IllegalStateException("다른 사용자의 정보를 수정할 수 없습니다.");
        }

        log.debug("Update User : {}", updateUser);
        user.update(updateUser);
        userDao.update(user);
        return jspView("redirect:/");
    }

    @RequestMapping(value = "/users", method = RequestMethod.POST)
    public ModelAndView create(UserCreatedDto userCreatedDto) throws Exception {
        log.debug("User : {}", userCreatedDto);
        User user = new User(
                userCreatedDto.getUserId(),
                userCreatedDto.getPassword(),
                userCreatedDto.getName(),
                userCreatedDto.getEmail()
        );
        userDao.insert(user);
        return jspView("redirect:/");
    }

    @RequestMapping(value = "/users/loginForm", method = RequestMethod.GET)
    public ModelAndView loginForm() throws Exception {
        return jspView("/user/login.jsp");
    }

    @RequestMapping(value = "/users/login", method = RequestMethod.POST)
    public ModelAndView login(@RequestParam String userId, @RequestParam String password, HttpServletRequest request) throws Exception {
        User user = userDao.findByUserId(userId);

        if (user == null) {
            throw new NullPointerException("사용자를 찾을 수 없습니다.");
        }

        if (user.matchPassword(password)) {
            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            return jspView("redirect:/");
        } else {
            throw new IllegalStateException("비밀번호가 틀립니다.");
        }
    }

    @RequestMapping(value = "/users/logout", method = RequestMethod.GET)
    public ModelAndView logout(HttpServletRequest request) throws Exception {
        HttpSession session = request.getSession();
        session.removeAttribute("user");
        return jspView("redirect:/");
    }
}
