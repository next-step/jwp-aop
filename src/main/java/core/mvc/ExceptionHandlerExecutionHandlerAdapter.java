package core.mvc;

import core.mvc.tobe.ExceptionHandlerExecution;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandlerExecutionHandlerAdapter implements ExceptionHandlerAdapter {

    @Override
    public boolean supports(Object handler) {
        return handler instanceof ExceptionHandlerExecution;
    }

    @Override
    public ModelAndView handle(Throwable throwable, HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        return ((ExceptionHandlerExecution) handler).handle(throwable, request, response);
    }
}
