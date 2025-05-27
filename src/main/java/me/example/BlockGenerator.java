package me.example;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class BlockGenerator {
    

    private static ArrayList<String> findUsableBlocks(Grid grid) {
        ArrayList<String> usableBlocks = new ArrayList<>();
        int[][] gameBoard = grid.getBoard();
        for (String blockName : Block.BLOCKS.keySet()) {
            int[][] block = Block.BLOCKS.get(blockName);
            for (int i = 0; i < 4; i++) { // Check all 4 
                String blockUsable = isBlockUsable(gameBoard, block);
                if (!blockUsable.equals("false")) {
                    usableBlocks.add(blockName + "\n" + i + "\n" + blockUsable);
                }
                block = Block.rotateCounterClockwise90(block); // Rotate the block
            }
        }
        return usableBlocks;
    }

    private static String isBlockUsable(int[][] gameBoard, int[][] block){
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
                if (canPlace) return Integer.toString(i) + " " + Integer.toString(j);
            }
        }
        return "false";
    }

    public static List<Block> getRandomUsableBlocks(Grid grid) {
        ArrayList<String> usableBlocks = findUsableBlocks(grid);
        Random random = new Random();
        List<Block> result = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            if (usableBlocks.isEmpty()) {
                break; // No more usable blocks
            }
            int randomIndex = random.nextInt(usableBlocks.size());
            String[] parts = usableBlocks.get(randomIndex).split("\n");
            String blockName = parts[0];
            int orientation = Integer.parseInt(parts[1]);
            usableBlocks.remove(randomIndex); // Remove to avoid duplicates
            result.add(new Block(blockName, orientation));
        }
        return result;
    }

    public static int[][] simulateBlockPlacement(Grid grid, int[][] blockShape, int row, int col) {
        int[][] gameBoard = grid.getBoard();
        int[][] newBoard = new int[gameBoard.length][gameBoard[0].length];

        // Copy the current game board to the new board
        for (int i = 0; i < gameBoard.length; i++) {
            System.arraycopy(gameBoard[i], 0, newBoard[i], 0, gameBoard[i].length);
        }

        // Place the block on the new board
        for (int i = 0; i < blockShape.length; i++) {
            for (int j = 0; j < blockShape[0].length; j++) {
                if (blockShape[i][j] == 1) {
                    newBoard[row + i][col + j] = 1;
                }
            }
        }
        return newBoard;
    }
}
