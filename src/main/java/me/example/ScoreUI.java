package me.example;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoreUI extends JPanel {
    private JLabel scoreLabel;
    private ScoreManager scoreManager;

    public ScoreUI(ScoreManager scoreManager) {
        this.scoreManager = scoreManager;
        scoreLabel = new JLabel("Score: 0");
        add(scoreLabel);
    }

    public void updateScore() {
        scoreLabel.setText("Score: " + scoreManager.getScore());
    }
    
}
