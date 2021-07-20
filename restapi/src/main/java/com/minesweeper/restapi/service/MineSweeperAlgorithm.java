package com.minesweeper.restapi.service;

import com.minesweeper.restapi.entity.Cell;
import com.minesweeper.restapi.entity.CellState;

import java.util.HashSet;
import java.util.List;
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

    protected static void mineGenerator(Character[][] cells, int rows, int columns, int mines) {
        int i = 0;
        while (i < mines) {
            int x = random.nextInt(columns - 1);
            int y = random.nextInt(rows - 1);
            if (CellState.MINE.label != (cells[x][y])) {
                cells[x][y] = CellState.MINE.label;
                i++;
            }
        }
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
    }*/

    protected static void numberGenerator(Character[][] cells, int rows, int columns) {
        int count = 0;
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (cells[i][j] == -1) {
                    count = 0;
                    for (int k = -1; k <= 1; k++) {
                        for (int l = -1; l <= 1; l++) {
                            // In boundary cases
                            try {
                                if (cells[i + k][j + l] != CellState.MINE.label) {
                                    count++;
                                    cells[i + k][j + l] = (char) (count);
                                }
                            } catch (ArrayIndexOutOfBoundsException e) {
                                // Do nothing
                            }
                        }
                    }
                }
            }
        }
    }

    private static int countSurround(Character[][] cells, int i, int j, int rows, int columns) {
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
    }

    private static <E, R> Function<E, Stream<R>> filterAndMap(Predicate<? super E> filter,
                                                              Function<? super E, R> mapper) {
        return e -> filter.test(e) ? Stream.of(mapper.apply(e)) : Stream.empty();
    }
}
