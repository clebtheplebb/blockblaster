package me.example;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;

public class GridUI extends JPanel{
    private Grid grid;
    private static final int CELL_SIZE = 32;

    public GridUI(Grid grid) {
        this.grid = grid;
        setPreferredSize(new java.awt.Dimension(Grid.SIZE * CELL_SIZE, Grid.SIZE * CELL_SIZE));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] board = grid.getBoard();
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                if (board[i][j] == 0) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.BLUE);
                }
                g.fillRect(j * CELL_SIZE+32, i * CELL_SIZE+32, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(j * CELL_SIZE+32, i * CELL_SIZE+32, CELL_SIZE, CELL_SIZE);
            }
        }
    }
}
