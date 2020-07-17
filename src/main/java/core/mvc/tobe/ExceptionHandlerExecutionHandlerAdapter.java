package core.mvc.tobe;

import core.mvc.ExceptionHandlerAdapter;
import core.mvc.ModelAndView;
import core.mvc.exception.ExceptionHandlerExecutionException;

public class ExceptionHandlerExecutionHandlerAdapter implements ExceptionHandlerAdapter {
    @Override
    public boolean supports(Object handler) {
        return handler instanceof ExceptionHandlerExecution;
    }

    @Override
    public ModelAndView handle(Throwable throwable, Object handler) throws ExceptionHandlerExecutionException {
        return ((ExceptionHandlerExecution) handler).handle(throwable);
    }
}
