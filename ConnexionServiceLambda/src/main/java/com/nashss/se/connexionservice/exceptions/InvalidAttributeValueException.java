package com.nashss.se.connexionservice.exceptions;

import javax.naming.directory.InvalidAttributesException;

/**
 * Exception to throw when a provided value has invalid attribute values.
 */
public class InvalidAttributeValueException extends InvalidAttributesException {

    private static final long serialVersionUID = 8007453316698012851L;

    /**
     * Exception with no message or cause.
     */
    public InvalidAttributeValueException() {
        super();
    }

    /**
     * Exception with a message, but no cause.
     * @param message A descriptive message for this exception.
     */
    public InvalidAttributeValueException(String message) {
        super(message);
    }

    /**
     * Exception with no message, but with a cause.
     * @param cause The original throwable resulting in this exception.
     */
    public InvalidAttributeValueException(Throwable cause) {
        super(cause.toString());
    }

    /**
     * Exception with message and cause.
     * @param message A descriptive message for this exception.
     * @param cause The original throwable resulting in this exception.
     */
    public InvalidAttributeValueException(String message, Throwable cause) {
        super(message);
    }
}
