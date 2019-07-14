package by.iba.web.exception;

public class ServiceException extends Exception {
  public ServiceException() {
    super();
  }

  public ServiceException(String message) {
    super(message);
  }

  public ServiceException(String message, Exception throwable) {
    super(message, throwable);
  }

  public ServiceException(Exception throwable) {
    super(throwable);
  }
}
