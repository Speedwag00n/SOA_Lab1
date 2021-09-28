package ilia.nemankov.utils;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MissingEntityException extends RuntimeException {

    private Error error;

    public MissingEntityException(Error error) {
        super();
        this.error = error;
    }

    public MissingEntityException(Error error, String message, Throwable cause) {
        super(message, cause);
        this.error = error;
    }

    public MissingEntityException(Error error, String message) {
        super(message);
        this.error = error;
    }

    public MissingEntityException(Error error, Throwable cause) {
        super(cause);
        this.error = error;
    }

}
