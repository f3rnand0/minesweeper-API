package com.minesweeper.restapi.service;

import com.minesweeper.restapi.dto.BoardDto;
import com.minesweeper.restapi.entity.Cell;

import java.util.ArrayList;
import java.util.List;

public class DataStructureTransformer {
    protected static List<Cell> transformFromArraysIntoListDto(BoardDto boardDto, int rows, int columns) {
        Boolean[][] visibleCells = boardDto.getVisibleCells();
        String[][] cells = boardDto.getCells();
        List<Cell> cellList = new ArrayList<>(rows * columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cellList.add(new Cell(i, j, visibleCells[i][j], cells[i][j]));
            }
        }
        return cellList;
    }

    protected static BoardDto transformFromListDtoIntoArrays(List<Cell> cellList, int rows, int columns) {
        Boolean[][] visibleCells = new Boolean[rows][columns];
        String[][] cells = new String[rows][columns];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = cellList.get(index);
                cells[i][j] = cell.getState();
                visibleCells[i][j] = cell.getVisible();
                index++;
            }
        }
        return new BoardDto(visibleCells, cells);
    }

}
