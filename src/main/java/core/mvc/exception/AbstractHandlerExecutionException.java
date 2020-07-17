package core.mvc.exception;

import java.lang.reflect.InvocationTargetException;

public abstract class AbstractHandlerExecutionException extends RuntimeException implements TargetThrowableGettable {
    protected Throwable throwable;

    public AbstractHandlerExecutionException(Throwable throwable) {
        super(throwable.getMessage(), throwable.getCause());
        this.throwable = throwable;
    }

    @Override
    public Throwable getTargetThrowable() {
        if (throwable instanceof InvocationTargetException) {
            return ((InvocationTargetException) throwable).getTargetException();
        }

        return throwable;
    }
}
