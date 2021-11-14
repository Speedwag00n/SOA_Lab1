package ilia.nemankov.service;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
public class BadResponseException extends Exception implements Serializable {

    private static final long serialVersionUID = 4656205826456998061L;
    private final int responseCode;

    public BadResponseException(String message, int responseCode) {
        super(message);
        this.responseCode = responseCode;
    }

    public BadResponseException(String message, Throwable cause, int responseCode) {
        super(message, cause);
        this.responseCode = responseCode;
    }

}
