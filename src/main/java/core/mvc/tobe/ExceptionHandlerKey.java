package core.mvc.tobe;

public class ExceptionHandlerKey extends HandlerKey {
    private final Exception exception;

    public ExceptionHandlerKey(Exception exception) {
        super("", null);
        this.exception = exception;
    }

    @Override
    public boolean isMatch(HandlerKey handlerKey) {
        if (!(handlerKey instanceof ExceptionHandlerKey)) {
            return false;
        }

        return exception.getClass() == ((ExceptionHandlerKey) handlerKey).exception.getClass();
    }
}
