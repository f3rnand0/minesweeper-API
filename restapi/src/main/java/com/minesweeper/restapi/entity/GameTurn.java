package com.minesweeper.restapi.entity;

public enum GameTurn {
    ZERO("zero"),
    FIRST("first"),
    LATER("later");

    public final String label;

    GameTurn(String label) {
        this.label = label;
    }
}
