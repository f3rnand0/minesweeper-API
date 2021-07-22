package com.minesweeper.restapi.entity;

public enum GameMessages {
    WON("Congratulations, you won!!"),
    LOST("Game over!!");

    public final String label;

    GameMessages(String label) {
        this.label = label;
    }

}
