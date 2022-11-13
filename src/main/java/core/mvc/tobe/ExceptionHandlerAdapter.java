package core.mvc.tobe;

import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ExceptionHandlerAdapter extends SupportableHandler{

    ModelAndView handle(Throwable throwable, HttpServletRequest request, HttpServletResponse response, Object handler) throws Throwable;
}
