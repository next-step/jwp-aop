package core.mvc;

import core.mvc.tobe.HandlerExecution;

public interface ExceptionHandlerMapping {
    void initialize();

    HandlerExecution getExceptionHandler(Throwable throwable, Object handler);
}
