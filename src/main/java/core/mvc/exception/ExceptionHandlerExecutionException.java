package core.mvc.exception;

public class ExceptionHandlerExecutionException extends AbstractHandlerExecutionException {
    public ExceptionHandlerExecutionException(Throwable throwable) {
        super(throwable);
    }
}
