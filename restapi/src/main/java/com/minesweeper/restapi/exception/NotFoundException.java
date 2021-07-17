package com.minesweeper.restapi.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
