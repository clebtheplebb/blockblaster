package me.example;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static int[][] GAME_BOARD = new int[8][8];
    private static final int FPS = 32;
    private static final long FRAME_TIME_MS = 1000 / FPS; //frame time in milliseconds

    public static void main(String[] args) {
        boolean running = true;
        while (running) {
            long currentTime = System.currentTimeMillis();
            long lastUpateTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastUpateTime;


            update();
            render();

            long sleepTime = (long) (FRAME_TIME_MS - elapsedTime);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            lastUpateTime = System.currentTimeMillis();
        }
    }

    private static HashMap<String, ArrayList<Integer>> findUsableBlocks(int[][] gameBoard) {
        HashMap<String, ArrayList<Integer>> usableBlocks = new HashMap<>();
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

    public static String hashMapToString(HashMap<String, ArrayList<Integer>> map) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(key).append(": ").append(map.get(key)).append("\n");
        }
        return sb.toString();
    }

    private static void update() {

    }

    private static void render() {

    }

}