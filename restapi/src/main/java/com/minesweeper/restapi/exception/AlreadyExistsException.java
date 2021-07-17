package com.minesweeper.restapi.exception;

import lombok.NoArgsConstructor;

public class AlreadyExistsException extends RuntimeException {

    public AlreadyExistsException(String message) {
        super(message);
    }
}
