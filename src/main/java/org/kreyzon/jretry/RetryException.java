package org.kreyzon.jretry;

public class RetryException extends RuntimeException {
    public RetryException(String message, Throwable cause) {
        super(message, cause);
    }
}
