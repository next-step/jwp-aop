package core.mvc.exception;

public class HandlerExecutionException extends AbstractHandlerExecutionException {
    private final String methodName;

    public HandlerExecutionException(Throwable throwable, String methodName) {
        super(throwable);
        this.methodName = methodName;
    }
}