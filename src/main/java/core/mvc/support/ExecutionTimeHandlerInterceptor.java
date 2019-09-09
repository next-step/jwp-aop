package core.mvc.support;

import core.mvc.HandlerInterceptor;
import core.mvc.ModelAndView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExecutionTimeHandlerInterceptor implements HandlerInterceptor {

    private static final Logger logger =
            LoggerFactory.getLogger(ExecutionTimeHandlerInterceptor.class);

    private static final String EXECUTION_TIME_ATTR_NAME = "executeTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        long startTime = System.currentTimeMillis();
        request.setAttribute(EXECUTION_TIME_ATTR_NAME, startTime);
        logger.debug("## [EXECUTION TIME] [{}] START", request.getRequestURI());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView mav) {
        final long startTime = (long) request.getAttribute(EXECUTION_TIME_ATTR_NAME);
        final long executionTime = System.currentTimeMillis() - startTime;

        logger.debug("## [EXECUTION TIME] [{}] END : {} millis", request.getRequestURI(),
                executionTime);
    }

}
