package com.minesweeper.restapi.entity;

public enum GameTurn {
    ZERO('z'),
    FIRST('f'),
    LATER('l');

    public final Character label;

    GameTurn(Character label) {
        this.label = label;
    }
}
