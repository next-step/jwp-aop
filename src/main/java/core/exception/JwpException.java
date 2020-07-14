package core.exception;

public class JwpException extends RuntimeException {
    public JwpException(JwpExceptionStatus status, Throwable e) {
        super(status.getMessage(), e);
    }
}
