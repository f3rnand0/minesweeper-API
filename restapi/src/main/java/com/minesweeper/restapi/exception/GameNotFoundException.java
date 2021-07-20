package com.minesweeper.restapi.exception;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class GameNotFoundException extends RuntimeException {

    public GameNotFoundException(String message) {
        super(message);
    }
}
