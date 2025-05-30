package me.example;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

public class Grid {
    private int[][] board;
    public static final int SIZE = 8;
    public Color[][] blockColors = new Color[SIZE][SIZE];

    public Grid() {
        board = new int[SIZE][SIZE];
    }

    public int[][] getBoard() {
        return board;
    }

    public int getCell(int x, int y) {
        return board[x][y];
    }

    public void setCell(int x, int y, int value) {
        board[x][y] = value;
    }

    public int getSize() {
        return SIZE;
    }

    public void setBoard(int[][] newBoard) {
        this.board = newBoard;
    }

    public Color[][] getBlockColors() {
        return blockColors;
    }

    public void reset() {
        board = new int[SIZE][SIZE];
        blockColors = new Color[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                blockColors[i][j] = null;
            }
        }
    }
}
