package com.nashss.se.connexionservice.exceptions;

/**
 * Exception to throw when a given user is not found in the database.
 */
public class UserNotFoundException extends RuntimeException {

    private static final long serialVersionUID = -912326717789387971L; //  GENERATE NEW ID!

    /**
     * Exception with no message or cause.
     */
    public UserNotFoundException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public UserNotFoundException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public UserNotFoundException(Throwable cause) {
        super(cause);
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public UserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
