package core.mvc;

import core.mvc.exception.ExceptionHandlerExecutionException;

public interface ExceptionHandlerAdapter {
    boolean supports(Object handler);

    ModelAndView handle(Throwable throwable, Object handler) throws ExceptionHandlerExecutionException;
}
