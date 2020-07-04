package next.security;

import core.mvc.tobe.MethodParameter;
import core.mvc.tobe.support.AbstractAnnotationArgumentResolver;
import next.controller.UserSessionUtils;
import next.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginUserArgumentResolver extends AbstractAnnotationArgumentResolver {
    private static final Logger logger = LoggerFactory.getLogger(LoginUserArgumentResolver.class);

    @Override
    public boolean supports(MethodParameter methodParameter) {
        return supportAnnotation(methodParameter, LoginUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter,
                                  HttpServletRequest request,
                                  HttpServletResponse response) {
        if (UserSessionUtils.isLogined(request.getSession())) {
            User loginUser = UserSessionUtils.getUserFromSession(request.getSession());
            logger.debug("Logined User : {}", loginUser);
            return loginUser;
        }

        if (requiredLogin(methodParameter)) {
            throw new RequiredLoginException("Login Required!!");
        }

        return User.GUEST_USER;
    }

    private boolean requiredLogin(MethodParameter methodParameter) {
        LoginUser loginUser = getAnnotation(methodParameter, LoginUser.class);
        return loginUser.required();
    }
}
