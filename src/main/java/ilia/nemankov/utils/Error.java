package ilia.nemankov.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Error {
    private int errorId;

    private String message;
}
