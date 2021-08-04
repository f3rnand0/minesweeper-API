package com.minesweeper.restapi.service;

import com.minesweeper.restapi.dto.BoardDto;
import com.minesweeper.restapi.entity.CellState;
import com.minesweeper.restapi.entity.GameMessages;

import java.util.Random;

public class MineSweeperAlgorithm {

    private static Random random = new Random();

    /**
     * Generate random mines according to number of rows, columns and mines
     * @param cells     Arrays with cell states
     * @param rows      Number of rows
     * @param columns   Number of columns
     * @param mines     Number of mines
     * @param x         X position of the first selected cell
     * @param y         Y position of the first selected cell
     * @return Array of initial cell states, including mines
     */
    protected static String[][] mineGenerator(String[][] cells, int rows, int columns, int mines,
                                              int x, int y) {
        int i = 0;
        while (i < mines) {
            int xRand = random.nextInt(rows);
            int yRand = random.nextInt(columns);
            // Avoid to put a mine in first selected cell
            if (x != xRand || y != yRand) {
                if (CellState.MINE.label != (cells[xRand][yRand])) {
                    cells[xRand][yRand] = CellState.MINE.label;
                    i++;
                }
            }
        }
        return cells;
    }

    /**
     * Generate corresponding numbers on surrounding cells with mines
     * @param cells   Arrays with cell states
     * @param rows    Number of rows
     * @param columns Number of columns
     * @return Array of cell states, including numbers
     */
    protected static String[][] numberGenerator(String[][] cells, int rows, int columns) {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (CellState.MINE.label.equals(cells[i][j])) {
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {
                            // In boundary cases
                            try {
                                if (!CellState.MINE.label.equals(cells[i + k][j + l])) {
                                    if (CellState.EMPTY.label.equals(cells[i + k][j + l]))
                                        cells[i + k][j + l] = String.valueOf(1);
                                    else
                                        cells[i + k][j + l] =
                                                String.valueOf(Integer.valueOf(cells[i + k][j + l]) + 1);
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                // Do nothing
                            }
                        }
                    }
                }
            }
        }
        return cells;
    }

    /**
     * Show a cell or cells, or finish the game based on the selected cell
     * @param boardDto
     * @param x        X position of the selected cell
     * @param y        Y position of the selected cell
     * @param mines    Number of mines
     * @return Arrays of cell states updated
     */
    protected static BoardDto checkSelectedCell(BoardDto boardDto, int x, int y, int mines) {
        Boolean[][] visibleCells = boardDto.getVisibleCells();
        Boolean[][] flaggedCells = boardDto.getFlaggedCells();
        String[][] cells = boardDto.getCells();
        if (!visibleCells[x][y] && !flaggedCells[x][y]) {
            visibleCells[x][y] = true;

            // Clicked on a mine, game over!
            if (CellState.MINE.label.equals(cells[x][y])) {
                boardDto.setEndMessage(GameMessages.LOST.label);
            }
            // Clicked on a empty cell, maybe won or continue
            else if (CellState.EMPTY.label.equals(cells[x][y])) {
                boardDto.setVisibleCount(boardDto.getVisibleCount() + 1);
                if (gameWon(boardDto.getVisibleCount(), mines, cells.length, cells[x].length)) {
                    boardDto.setEndMessage(GameMessages.WON.label);
                }

                // Else simply recurse around
                for (int i = -1; i <= 1; i++) {
                    for (int j = -1; j <= 1; j++) {
                        try {
                            checkSelectedCell(boardDto, x + i, y + j, mines);
                        } catch (ArrayIndexOutOfBoundsException e) {
                            // Do nothing
                        }
                    }
                }
            }
            // Clicked on a numbered, visible, or flagged cell
            else {
                boardDto.setVisibleCount(boardDto.getVisibleCount() + 1);
                if (gameWon(boardDto.getVisibleCount(), mines, cells.length, cells[x].length)) {
                    boardDto.setEndMessage(GameMessages.WON.label);
                }
            }
        }
        boardDto.setVisibleCells(visibleCells);
        boardDto.setCells(cells);
        return boardDto;
    }

    /**
     * Validates if the number of visible cells plus mines is equal to the total number of cells
     * @param visibleCount Number of visible cells
     * @param mines        Number of mines
     * @param rows         Number of rows
     * @param columns      Number of mines
     * @return Boolean indicating if the game has finished
     */
    private static Boolean gameWon(int visibleCount, int mines, int rows, int columns) {
        return (visibleCount + mines) == (rows * columns);
    }

    /**
     * Flags a selected cell
     * @param visibleCell Row and column of the visible cell
     * @param flaggedCells Array of flagged cells
     * @param x            X position of the selected cell
     * @param y            Y position of the selected cell
     * @return Array of flagged cells
     */
    protected static Boolean[][] flagSelectedCell(Boolean visibleCell, Boolean[][] flaggedCells, int x, int y) {
        // Only flag a hidden cell
        if (!visibleCell) {
            // Flag a cell
            if (flaggedCells[x][y])
                flaggedCells[x][y] = Boolean.FALSE;
                // Otherwise undo the flag
            else
                flaggedCells[x][y] = Boolean.TRUE;
        }
        return flaggedCells;
    }

}
