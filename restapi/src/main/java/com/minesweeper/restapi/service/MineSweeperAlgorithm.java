package com.minesweeper.restapi.service;

import com.minesweeper.restapi.dto.BoardDto;
import com.minesweeper.restapi.entity.CellState;

import java.util.Random;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class MineSweeperAlgorithm {

    private static Random random = new Random();

    /*public static List<Cell> mineGenerator(List<Cell> cellSet, int rows, int columns, int mineNumber) {
        int i = 0;
        while (i < mineNumber) {
            int x = random.nextInt(columns - 1);
            int y = random.nextInt(rows - 1);
            Predicate<Cell> filterCellXY = c -> c.getRow().equals(x) && c.getColumn().equals(y);
            //cellSet.stream().flatMap(filterAndMap(predicate, c -> c.setState(CellState.MINE))).findFirst
            ().get();
            Cell cell = cellSet.stream().filter(filterCellXY).findFirst().get();
            if (!CellState.MINE.equals(cell.getState())) {
                cellSet.stream().filter(c -> c.getRow().equals(x) && c.getColumn().equals(y))
                        .map(c -> c.setState(CellState.MINE));
                i++;
            }
        }
        return cellSet;
    }*/

    protected static String[][] mineGenerator(String[][] cells, int rows, int columns, int mines) {
        int i = 0;
        while (i < mines) {
            int x = random.nextInt(columns - 1);
            int y = random.nextInt(rows - 1);
            if (CellState.MINE.label != (cells[x][y])) {
                cells[x][y] = CellState.MINE.label;
                i++;
            }
        }
        return cells;
    }

    protected static String[][] numberGenerator(String[][] cells, int rows, int columns) {
        int value = 0;
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
                                        cells[i + k][j + l] = String.valueOf(Integer.valueOf(cells[i + k][j + l]) + 1);
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

    protected static BoardDto checkSelectedCell(BoardDto boardDto, int x, int y, int mines) {
        Boolean[][] visibleCells = boardDto.getVisibleCells();
        String[][] cells = boardDto.getCells();
        if (!visibleCells[x][y] && !CellState.FLAGGED.label.equals(cells[x][y])) {
            visibleCells[x][y] = true;

            // Clicked on a mine, game over!
            if (CellState.MINE.equals(cells[x][y])) {
                boardDto.setEndMessage("Game Over");
            }
            // Clicked on a empty cell, maybe won or continue
            else if (CellState.EMPTY.equals(cells[x][y])) {
                boardDto.setVisibleCount(boardDto.getVisibleCount()+1);
                if (gameWon(boardDto.getVisibleCount(), mines, cells.length, cells[x].length)) {
                    boardDto.setEndMessage("You won");
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
                boardDto.setVisibleCount(boardDto.getVisibleCount()+1);
                if (gameWon(boardDto.getVisibleCount(), mines, cells.length, cells[x].length)) {
                    boardDto.setEndMessage("You won");
                }
            }
        }
        boardDto.setVisibleCells(visibleCells);
        boardDto.setCells(cells);
        return boardDto;
    }

    private static boolean gameWon(int visibleCount, int mines, int rows, int columns) {
        return (visibleCount + mines) == (rows * columns);
    }

    /*protected static void numberGenerator(Character[][] cells, int rows, int columns) {
            for (int i = 0; i < columns; i++) {
                for (int j = 0; j < rows; j++) {
                    if (CellState.MINE.label != (cells[i][j]))
                        cells[i][j] = (char) (countSurround(cells, i, j, rows, columns) + 48);
                    if (cells[i][j] == '0')
                        cells[i][j] = CellState.HIDDEN.label;
                }
            }
     }

    private static int countSurround(String[][] cells, int i, int j, int rows, int columns) {
        int count = 0;
        if (i + 1 != columns && cells[i + 1][j] == CellState.MINE.label)
            count++;
        if (i + 1 != columns && j + 1 != rows && cells[i + 1][j + 1] == CellState.MINE.label)
            count++;
        if (i - 1 != -1 && j + 1 != rows && cells[i - 1][j + 1] == CellState.MINE.label)
            count++;
        if (i + 1 != columns && j - 1 != -1 && cells[i + 1][j - 1] == CellState.MINE.label)
            count++;
        if (i - 1 != -1 && j - 1 != -1 && cells[i - 1][j - 1] == CellState.MINE.label)
            count++;
        if (i - 1 != -1 && cells[i - 1][j] == CellState.MINE.label)
            count++;
        if (j + 1 != rows && cells[i][j + 1] == CellState.MINE.label)
            count++;
        if (j - 1 != -1 && cells[i][j - 1] == CellState.MINE.label)
            count++;
        return count;
    }*/

    private static <E, R> Function<E, Stream<R>> filterAndMap(Predicate<? super E> filter,
                                                              Function<? super E, R> mapper) {
        return e -> filter.test(e) ? Stream.of(mapper.apply(e)) : Stream.empty();
    }
}
