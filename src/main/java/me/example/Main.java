package me.example;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    private static final int FPS = 32;
    private static final long FRAME_TIME_MS = 1000 / FPS; //frame time in milliseconds

    public static void main(String[] args) {
        Grid grid = new Grid();
        GUI gui = new GUI(grid);
        gui.setVisible(true);
        boolean running = true;
        boolean round = false;
        while (running) {
            long currentTime = System.currentTimeMillis();
            long lastUpateTime = System.currentTimeMillis();
            long elapsedTime = currentTime - lastUpateTime;

            //update
            //render
            if (round == false) {
                gui.refresh();
                round = true;
            }

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

    public static String hashMapToString(HashMap<String, ArrayList<Integer>> map) {
        StringBuilder sb = new StringBuilder();
        for (String key : map.keySet()) {
            sb.append(key).append(": ").append(map.get(key)).append("\n");
        }
        return sb.toString();
    }
}