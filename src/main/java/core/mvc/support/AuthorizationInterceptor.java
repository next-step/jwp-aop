package core.mvc.support;

import core.mvc.HandlerInterceptor;
import core.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthorizationInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(AuthorizationInterceptor.class);

    private static final String ADMIN = "admin";
    private static final String ADMIN_URL = "/admin";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {

        if (!request.getRequestURI().startsWith(ADMIN_URL)) {
            logger.debug("## [AUTHORIZATION] USER URL [{}]", request.getRequestURI());
            return true;
        }

        if (ADMIN.equals(request.getParameter("role"))) {
            logger.debug("## [AUTHORIZATION] ADMIN URL [{}] is ok", request.getRequestURI());
            return true;
        }

        logger.debug("## [AUTHORIZATION] ADMIN URL [{}] forbidden", request.getRequestURI());
        response.setStatus(HttpStatus.FORBIDDEN.value());
        return false;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) {
        logger.debug("## [AUTHORIZATION] no authorization end action");
    }

}
