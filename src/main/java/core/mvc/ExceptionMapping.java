package core.mvc;

public interface ExceptionMapping {

    void initialize();

    ExceptionHandler getHandler(Throwable throwable);

}
