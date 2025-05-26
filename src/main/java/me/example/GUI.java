package me.example;

import javax.swing.*;
import java.awt.*;

public class GUI extends JFrame {
    private Grid grid;
    private GridUI gridUI;
    private static final int CELL_SIZE = 32;

    public GUI(Grid grid) {
        this.grid = grid;
        this.gridUI = new GridUI(grid);
        setTitle("Block Blaster");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(Grid.SIZE * CELL_SIZE + 100, Grid.SIZE * CELL_SIZE + 200); // Account for window borders
        setLocationRelativeTo(null);
        setResizable(false);
        add(gridUI);
    }

    public void refresh() {
        repaint();
    }
}
