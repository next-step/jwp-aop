package core.mvc.tobe;

public interface ExceptionHandlerMapping {

    HandlerExecution getExceptionHandler(Object handler, Throwable throwable);
}
