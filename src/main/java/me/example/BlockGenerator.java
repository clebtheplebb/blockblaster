package me.example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.awt.Color;

public class BlockGenerator {

    public static List<Color> colors = Arrays.asList(
            Color.decode("#45B94E"), Color.decode("#41B8E3"), Color.decode("#D83D3D"), Color.decode("#D83D3D"), Color.decode("#D83D3D"), Color.decode("#D83D3D"), Color.decode("#F1B94D"), Color.decode("#EC724C")
    );
    
    private static ArrayList<Block> findUsableBlocks(Grid grid) {
        ArrayList<Block> usableBlocks = new ArrayList<>();
        int[][] gameBoard = grid.getBoard();
        for (String blockName : Block.BLOCKS.keySet()) {
            for (int i = 0; i < 4; i++) { // Check all 4 
                Block block = new Block(blockName, i);
                String blockUsable = isBlockUsable(gameBoard, block);
                if (!blockUsable.equals("false")) {
                    String[] parts = blockUsable.split(" ");
                    int row = Integer.parseInt(parts[0]);
                    int col = Integer.parseInt(parts[1]);
                    usableBlocks.add(new Block(blockName, i, col, row));
                }
            }
        }
        return usableBlocks;
    }

    public static String isBlockUsable(int[][] gameBoard, Block block){
        for (int i = 0; i <= gameBoard.length - block.getShape().length; i++) {
            for (int j = 0; j <= gameBoard[0].length - block.getShape()[0].length; j++) {
                boolean canPlace = true;
                for (int x = 0; x < block.getShape().length; x++) {
                    for (int y = 0; y < block.getShape()[0].length; y++) {
                        if (block.getShape()[x][y] == 1 && gameBoard[i + x][j + y] != 0) {
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

    public static List<Block> getThreeUsableBlocks(Grid grid) {
        // fix this method to return 3 random usable blocks
        ArrayList<Block> usableBlocks = findUsableBlocks(grid);
        Random random = new Random();
        List<Block> result = new ArrayList<>();
        for (int i = 0; i < 3 && !usableBlocks.isEmpty(); i++) {
            int index = random.nextInt(usableBlocks.size());
            Block block = usableBlocks.get(index);
            block.setColor(colors.get(random.nextInt(colors.size())));
            result.add(block);
            usableBlocks.remove(index);
        }
        return result;
    }

    // public static Grid simulateBlockPlacement(Grid grid, int[][] blockShape, int row, int col) {
    //     int[][] gameBoard = grid.getBoard();
    //     int[][] newBoard = Arrays.copyOf(gameBoard, gameBoard.length);

    //     // Place the block on the new board
    //     for (int i = 0; i < blockShape.length; i++) {
    //         for (int j = 0; j < blockShape[0].length; j++) {
    //             if (blockShape[i][j] == 1) {
    //                 newBoard[row + i][col + j] = 1;
    //             }
    //         }
    //     }

    //     int[] rowTotals = new int[Grid.SIZE];
    //     int[] colTotals = new int[Grid.SIZE];

    //     // Calculate row and column totals
    //     for (int i = 0; i < Grid.SIZE; i++) {
    //         for (int j = 0; j < Grid.SIZE; j++) {
    //             rowTotals[i] += newBoard[i][j];
    //             colTotals[j] += newBoard[i][j];
    //         }
    //     }

    //     // Check for completed rows and columns
    //     for (int r = 0; r < Grid.SIZE; r++) {
    //         if (rowTotals[r] == Grid.SIZE) {
    //             // Clear the row
    //             for (int j = 0; j < Grid.SIZE; j++) {
    //                 newBoard[r][j] = 0;
    //             }
    //         }
    //     }

    //     for (int c = 0; c < Grid.SIZE; c++) {
    //         if (colTotals[c] == Grid.SIZE) {
    //             // Clear the column
    //             for (int i = 0; i < Grid.SIZE; i++) {
    //                 newBoard[i][c] = 0;
    //             }
    //         }
    //     }

    //     return newBoard;
    // }
}
