package core.mvc.tobe;

import core.mvc.ExceptionHandlerAdapter;
import core.mvc.HandlerAdapter;
import core.mvc.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ExceptionHandlerExecutionHandlerAdapter implements ExceptionHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof ExceptionHandlerExecution;
    }

    @Override
    public ModelAndView handle(Throwable throwable, Object handler) throws Exception {
        return ((ExceptionHandlerExecution) handler).handle(throwable);
    }
}
