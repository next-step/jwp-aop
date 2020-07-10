package core.mvc;

public interface ExceptionHandlerMapping {
    void initialize();

    Object getHandler(Throwable exception);
}
