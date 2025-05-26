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

    private static HashMap<String, ArrayList<Integer>> findUsableBlocks(Grid grid) {
        HashMap<String, ArrayList<Integer>> usableBlocks = new HashMap<>();
        int[][] gameBoard = grid.getBoard();
        for (String blockName : Block.BLOCKS.keySet()) {
            int[][] block = Block.BLOCKS.get(blockName);
            ArrayList<Integer> orientations = new ArrayList<Integer>();
            for (int i = 0; i < 4; i++) { // Check all 4 orientations
                if (isBlockUsable(gameBoard, block)) {
                    if (!usableBlocks.containsKey(blockName)) {
                        usableBlocks.put(blockName, orientations);
                    } else {
                        usableBlocks.get(blockName).add(i);
                    }
                }
                block = Block.rotateCounterClockwise90(block); // Rotate the block
            }
        }
        return usableBlocks;
    }

    private static boolean isBlockUsable(int[][] gameBoard, int[][] block){
        for (int i = 0; i <= gameBoard.length - block.length; i++) {
            for (int j = 0; j <= gameBoard[0].length - block[0].length; j++) {
                boolean canPlace = true;
                for (int x = 0; x < block.length; x++) {
                    for (int y = 0; y < block[0].length; y++) {
                        if (block[x][y] == 1 && gameBoard[i + x][j + y] != 0) {
                            canPlace = false;
                            break;
                        }
                    }
                    if (!canPlace) break;
                }
                if (canPlace) return true;
            }
        }
        return false;
    }
}
