package by.iba.web.exception;

public class UserNotFoundException extends RuntimeException {

  public UserNotFoundException() {
    super();
  }

  public UserNotFoundException(String message) {
    super(message);
  }

  public UserNotFoundException(String message, Exception throwable) {
    super(message, throwable);
  }

  public UserNotFoundException(Exception throwable) {
    super(throwable);
  }
}
