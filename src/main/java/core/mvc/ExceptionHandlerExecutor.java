package core.mvc;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExceptionHandlerExecutor {
    ModelAndView handle(Throwable exception, HttpServletRequest request, HttpServletResponse response) throws Throwable;
}
