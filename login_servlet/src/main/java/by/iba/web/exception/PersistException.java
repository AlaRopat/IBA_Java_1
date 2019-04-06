package by.iba.web.exception;

public class PersistException extends Exception {
    public PersistException() {
        super();
    }

    public PersistException(String message) {
        super(message);
    }

    public PersistException(String message, Exception throwable) {
        super(message, throwable);
    }

    public PersistException(Exception throwable) {
        super(throwable);
    }

}