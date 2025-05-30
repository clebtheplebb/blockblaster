package me.example;

import javax.swing.*;

import java.awt.*;
import java.util.List;

public class GUI extends JFrame {
    private Grid grid;
    private GameUI gameUI;
    private BlockDragController blockDragController;
    private ScoreManager scoreManager;
    private ScoreUI scoreUI;
    public static List<Block> currentBlocks;

    public GUI(Grid grid) {
        this(grid, new ScoreManager());
    }
    public GUI(Grid grid, ScoreManager scoreManager) {
        this.grid = grid;
        this.scoreManager = scoreManager;
        this.scoreUI = new ScoreUI(scoreManager);
        this.gameUI = new GameUI(grid, scoreManager);
        setTitle("Block Blaster");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Grid.SIZE * GameUI.GRID_CELL_SIZE + 80, Grid.SIZE * GameUI.GRID_CELL_SIZE + 200); // Account for window borders
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        getContentPane().setBackground(Color.decode("#3A4E83"));
        add(scoreUI, BorderLayout.NORTH);
        add(gameUI, BorderLayout.CENTER);
        this.blockDragController = new BlockDragController(gameUI);
    }

    public ScoreManager getScoreManager() {
        return scoreManager;
    }

    public void refresh() {
        GUI.currentBlocks = BlockGenerator.getThreeUsableBlocks(grid);
        if (scoreUI != null) scoreUI.updateScore();
        repaint();
    }

    public void showGameOver() {
        JOptionPane.showMessageDialog(this, "Game Over!", "Game Over", JOptionPane.INFORMATION_MESSAGE);
        // Restart the game
        if (scoreManager != null) scoreManager.reset();
        if (grid != null) grid.reset();
        GUI.currentBlocks = BlockGenerator.getThreeUsableBlocks(grid);
        repaint();
    }

    @Override
    public void repaint() {
        if (scoreUI != null) scoreUI.updateScore();
        super.repaint();
    }
}
