package me.example;

import java.util.ArrayList;
import java.util.HashMap;

public class Grid {
    private int[][] board;
    public static final int SIZE = 8;

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
}
