package core.transaction;

public class TransactionException extends RuntimeException {

    public TransactionException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
