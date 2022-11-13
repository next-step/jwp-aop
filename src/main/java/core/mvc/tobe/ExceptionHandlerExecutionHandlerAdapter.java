package core.mvc.tobe;

import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandlerExecutionHandlerAdapter implements ExceptionHandlerAdapter{
    @Override
    public ModelAndView handle(Throwable throwable, HttpServletRequest request, HttpServletResponse response, Object handler) throws Throwable {
        return ((ExceptionHandlerExecution) handler).handle(throwable, request, response);
    }

    @Override
    public boolean supports(Object handler) {
        return handler instanceof ExceptionHandlerExecution;
    }
}
