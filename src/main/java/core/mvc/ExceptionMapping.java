package core.mvc;

public interface ExceptionMapping {

    void initialize();

    ExceptionHandler getHandler(Class<? extends Exception> exceptionClass);

}
