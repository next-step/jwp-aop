package core.mvc;

public interface ExceptionHandlerMapping {
    void initialize();

    Object getHandler(Class<? extends Throwable> throwable);
}
