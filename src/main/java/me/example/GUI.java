package me.example;

import javax.swing.*;

import java.awt.*;
import java.util.List;

public class GUI extends JFrame {
    private Grid grid;
    private GameUI gameUI;
    private BlockDragController blockDragController;
    public static List<Block> currentBlocks;

    public GUI(Grid grid) {
        this.grid = grid;
        this.gameUI = new GameUI(grid);
        setTitle("Block Blaster");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Grid.SIZE * GameUI.GRID_CELL_SIZE + 80, Grid.SIZE * GameUI.GRID_CELL_SIZE + 200); // Account for window borders
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        add(gameUI, BorderLayout.CENTER);
        this.blockDragController = new BlockDragController(gameUI);
    }

    public void refresh() {
        GUI.currentBlocks = BlockGenerator.getThreeUsableBlocks(grid);
        repaint();
    }
}
