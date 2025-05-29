package me.example;

public class ScoreManager {
    private int score = 0;

    public void addBlockScore(int cells) {
        score += cells;
    }

    public void addClearScore(int lines) {
        score += lines * 50;
    }

    public int getScore() {
        return score;
    }

    public void reset() {
        score = 0;
    }
}
