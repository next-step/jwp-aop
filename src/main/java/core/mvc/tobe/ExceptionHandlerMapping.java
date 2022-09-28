package core.mvc.tobe;

public interface ExceptionHandlerMapping {

    ExceptionHandlerExecution getExceptionHandler(Object key);
}
