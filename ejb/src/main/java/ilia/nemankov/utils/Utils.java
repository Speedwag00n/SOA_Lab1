package ilia.nemankov.utils;

import ilia.nemankov.service.BadResponseException;

public class Utils {

    public static Exception serializeBadResponseException(BadResponseException e) {
        return new Exception("BadResponseException:" + e.getResponseCode() + ":" + e.getMessage());
    }

    public static BadResponseException deserializeBadResponseException(Exception e) throws Exception {
        try {
            Throwable cause = e.getCause();
            if (cause.getMessage().startsWith("BadResponseException:")) {
                String[] parts = cause.getMessage().split(":");

                return new BadResponseException(parts[2], Integer.parseInt(parts[1]));
            } else {
                throw e;
            }
        } catch (Exception e1) {
            throw e;
        }
    }

}
