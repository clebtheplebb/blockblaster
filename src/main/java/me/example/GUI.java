package me.example;

import javax.swing.*;

import java.awt.*;

public class GUI extends JFrame {
    private Grid grid;
    private GridUI gridUI;
    private BlockUI blockUI;
    private BlockDragController blockDragController;
    private static final int CELL_SIZE = 32;

    public GUI(Grid grid) {
        this.grid = grid;
        this.gridUI = new GridUI(grid);
        this.blockUI = new BlockUI(BlockGenerator.getThreeUsableBlocks(grid));
        setTitle("Block Blaster");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Grid.SIZE * CELL_SIZE + 80, Grid.SIZE * CELL_SIZE + 200); // Account for window borders
        setLocationRelativeTo(null);
        setResizable(false);
        setLayout(new BorderLayout());
        add(gridUI, BorderLayout.CENTER);
        add(blockUI, BorderLayout.SOUTH);
        // Add BlockDragController
        blockDragController = new BlockDragController(blockUI, gridUI, BlockGenerator.getThreeUsableBlocks(grid));
    }

    public void refresh() {
        blockUI.setBlocks(BlockGenerator.getThreeUsableBlocks(grid));
        repaint();
    }
}
