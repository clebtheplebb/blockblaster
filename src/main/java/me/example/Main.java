package me.example;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static final int FPS = 32;
    private static final long FRAME_TIME_MS = 1000 / FPS; //frame time in milliseconds

    public static void main(String[] args) {
        Grid grid = new Grid();
        ScoreManager scoreManager = new ScoreManager();
        GUI gui = new GUI(grid, scoreManager);
        gui.setVisible(true);
        boolean running = true;
        while (running) {
            long currentTime = System.currentTimeMillis();
            long lastUpdateTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastUpdateTime;

            //update
            //render
            boolean allPlaced = false;
            if (GUI.currentBlocks != null && !GUI.currentBlocks.isEmpty()) {
                allPlaced = true;
                for (Block block : GUI.currentBlocks) {
                    if (!block.isPlaced()) {
                        allPlaced = false;
                        break;
                    }
                }
            }
            if (allPlaced) {
                gui.refresh();
            }
            if (GUI.currentBlocks == null || GUI.currentBlocks.isEmpty()) {
                gui.refresh();
            }

            long sleepTime = (long) (FRAME_TIME_MS - elapsedTime);
            if (sleepTime > 0) {
                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }

            lastUpdateTime = System.currentTimeMillis();
        }
    }

    public static String hashMapToString(HashMap<String, ArrayList<Integer>> map) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(key).append(": ").append(map.get(key)).append("\n");
        }
        return sb.toString();
    }
}