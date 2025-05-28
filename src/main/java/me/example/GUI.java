package me.example;

import javax.swing.*;

import java.awt.*;
import java.util.List;

public class GUI extends JFrame {
    private Grid grid;
    private GridUI gridUI;
    private BlockUI blockUI;
    private List<Block> currentBlocks;
    private BlockDragController blockDragController;
    private static final int CELL_SIZE = 32;

    public GUI(Grid grid) {
        this.grid = grid;
        this.gridUI = new GridUI(grid);
        this.currentBlocks = BlockGenerator.getThreeUsableBlocks(grid);
        this.blockUI = new BlockUI(currentBlocks);
        setTitle("Block Blaster");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Grid.SIZE * CELL_SIZE + 80, Grid.SIZE * CELL_SIZE + 200); // Account for window borders
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        add(gridUI, BorderLayout.CENTER);
        add(blockUI, BorderLayout.SOUTH);
        // Add BlockDragController
        blockDragController = new BlockDragController(blockUI, gridUI, currentBlocks);
    }

    public void refresh() {
        repaint();
    }
}
