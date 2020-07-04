package next.security;

public class RequiredLoginException extends RuntimeException {
    public RequiredLoginException(String message) {
        super(message);
    }
}
