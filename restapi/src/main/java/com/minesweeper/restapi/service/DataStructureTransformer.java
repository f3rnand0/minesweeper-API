package com.minesweeper.restapi.service;

import com.minesweeper.restapi.dto.BoardDto;
import com.minesweeper.restapi.entity.Cell;

import java.util.ArrayList;
import java.util.List;

public class DataStructureTransformer {
    /**
     * Transforms arrays of visible, flagged and cell states into a list of Cell dtos
     *
     * @param boardDto Dto containing arrays of visible, flagged and cell states
     * @param rows     Number of rows
     * @param columns  numer of columns
     * @return List of Cell dtos containing which cells are visible, which are flagged and its states
     */
    protected static List<Cell> transformFromArraysIntoListDto(BoardDto boardDto, int rows, int columns) {
        Boolean[][] visibleCells = boardDto.getVisibleCells();
        Boolean[][] flaggedCells = boardDto.getFlaggedCells();
        String[][] cells = boardDto.getCells();
        List<Cell> cellList = new ArrayList<>(rows * columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                cellList.add(new Cell(i, j, visibleCells[i][j], flaggedCells[i][j], cells[i][j]));
            }
        }
        return cellList;
    }

    /**
     * Transforms arrays of visible, flagged and cell states into a list of Cell dtos
     *
     * @param cellList List of Cell dtos containing which cells are visible, which are flagged and its states
     * @param rows     Number of rows
     * @param columns  Number of columns
     * @return Dto containing arrays of visible, flagged and cell states
     */
    protected static BoardDto transformFromListDtoIntoArrays(List<Cell> cellList, int rows, int columns) {
        Boolean[][] visibleCells = new Boolean[rows][columns];
        Boolean[][] flaggedCells = new Boolean[rows][columns];
        String[][] cells = new String[rows][columns];
        int index = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                Cell cell = cellList.get(index);
                visibleCells[i][j] = cell.getVisible();
                flaggedCells[i][j] = cell.getFlagged();
                cells[i][j] = cell.getState();
                index++;
            }
        }
        return new BoardDto(visibleCells, flaggedCells, cells);
    }
}
