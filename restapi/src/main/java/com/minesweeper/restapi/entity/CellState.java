package com.minesweeper.restapi.entity;

public enum CellState {
    EMPTY("empty"),
    MINE("mine");

    public final String label;

    CellState(String label) {
        this.label = label;
    }

}
