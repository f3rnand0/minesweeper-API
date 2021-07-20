package com.minesweeper.restapi.entity;

public enum CellState {
    HIDDEN('h'),
    VISIBLE('v'),
    MINE('m'),
    //NUMBERED('n'),
    FLAGGED('f');

    public final Character label;

    CellState(Character label) {
        this.label = label;
    }

}
