package com.minesweeper.restapi.service;

public class MineSweeperAlgorithm {

/*    public void setup() {
        createBricks();
        MineGenerator();
        NumberGenerator();
    }

    private void createBricks() {
        for (int i = 0; i < TILE_COLUMNS; i++) {
            for (int j = 0; j < TILE_ROWS; j++) {
                Tile tile = new Tile(i * TILE_SIDE, j * TILE_SIDE, TILE_SIDE, TILE_SIDE);
                tile.setColor(new Color(100, 100, 100));
                tile.setRaised(true);
                tile.setFilled(true);
                add(tile);
            }
        }
    }

    private void MineGenerator() {
        int i = 0;
        while (i < MINE_NUMBER) {
            int x = rgen.nextInt(0, TILE_COLUMNS - 1);
            int y = rgen.nextInt(0, TILE_ROWS - 1);
            if (Mines[x][y] != 'x') {
                Mines[x][y] = 'x';
                i++;
            }
        }
    }

    private void NumberGenerator() {
        for (int i = 0; i < TILE_COLUMNS; i++) {
            for (int j = 0; j < TILE_ROWS; j++) {
                if (Mines[i][j] != 'x')
                    Mines[i][j] = (char) (CountSurround(i, j) + 48);
                if (Mines[i][j] == '0')
                    Mines[i][j] = ' ';
            }
        }
    }

    private int CountSurround(int i, int j) {
        int count = 0;
        if (i + 1 != TILE_COLUMNS && Mines[i + 1][j] == 'x')
            count++;
        if (i + 1 != TILE_COLUMNS && j + 1 != TILE_ROWS && Mines[i + 1][j + 1] == 'x')
            count++;
        if (i - 1 != -1 && j + 1 != TILE_ROWS && Mines[i - 1][j + 1] == 'x')
            count++;
        if (i + 1 != TILE_COLUMNS && j - 1 != -1 && Mines[i + 1][j - 1] == 'x')
            count++;
        if (i - 1 != -1 && j - 1 != -1 && Mines[i - 1][j - 1] == 'x')
            count++;
        if (i - 1 != -1 && Mines[i - 1][j] == 'x')
            count++;
        if (j + 1 != TILE_ROWS && Mines[i][j + 1] == 'x')
            count++;
        if (j - 1 != -1 && Mines[i][j - 1] == 'x')
            count++;
        return count;
    }*/
}
