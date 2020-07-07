package core.mvc;

public class MethodInvocationException extends RuntimeException {
    public MethodInvocationException(Exception e) {
        super("Fail to invoke method " + e.getMessage());
    }
}
