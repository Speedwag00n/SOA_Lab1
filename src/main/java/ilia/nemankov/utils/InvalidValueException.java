package ilia.nemankov.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InvalidValueException extends Exception {

    private Error error;

    public InvalidValueException(Error error) {
        super();
        this.error = error;
    }

    public InvalidValueException(Error error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

    public InvalidValueException(Error error, String message) {
        super(message);
        this.error = error;
    }

    public InvalidValueException(Error error, Throwable cause) {
        super(cause);
        this.error = error;
    }

}
