package transfer.api.exception;

public class SameAccountException extends RuntimeException {

    public SameAccountException(String message) {
        super(message);
    }
}
