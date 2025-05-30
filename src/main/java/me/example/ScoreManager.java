package me.example;

public class ScoreManager {
    private int score = 0;
    private boolean gameOver = false;

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
        gameOver = false;
    }

    public void gameOver() {
        this.gameOver = true;
    }

    public boolean isGameOver() {
        return gameOver;
    }
}
