package me.example;

import javax.swing.JPanel;

import me.example.Grid;

import java.awt.Graphics;
import java.awt.Color;

public class GridUI extends JPanel{
    private Grid grid;
    private static final int CELL_SIZE = 32;

    // Preview block fields
    private Block previewBlock = null;
    private Integer previewGridX = null, previewGridY = null;

    public GridUI(Grid grid) {
        this.grid = grid;
        setPreferredSize(new java.awt.Dimension(Grid.SIZE * CELL_SIZE, Grid.SIZE * CELL_SIZE));
    }

    // Set the preview block and its grid position
    public void setPreviewBlock(Block block, int gridX, int gridY) {
        this.previewBlock = block;
        this.previewGridX = gridX;
        this.previewGridY = gridY;
        repaint();
    }

    // Clear the preview block
    public void clearPreviewBlock() {
        this.previewBlock = null;
        this.previewGridX = null;
        this.previewGridY = null;
        repaint();
    }

    public Grid getGrid() {
        return grid;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] board = grid.getBoard();
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                if (board[i][j] == 0) {
                    g.setColor(Color.LIGHT_GRAY);
                } else if (board[i][j] == 2) {
                    g.setColor(Color.YELLOW);
                } else {
                    g.setColor(Color.BLUE);
                }
                g.fillRect(j * CELL_SIZE+32, i * CELL_SIZE+80, CELL_SIZE, CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(j * CELL_SIZE+32, i * CELL_SIZE+80, CELL_SIZE, CELL_SIZE);
            }
        }
        // Draw preview (ghost) block if set
        if (previewBlock != null && previewGridX != null && previewGridY != null) {
            int[][] shape = previewBlock.getShape();
            for (int i = 0; i < shape.length; i++) {
                for (int j = 0; j < shape[0].length; j++) {
                    if (shape[i][j] != 0) {
                        int x = (previewGridX + j) * CELL_SIZE + 32;
                        int y = (previewGridY + i) * CELL_SIZE + 80;
                        g.setColor(new Color(0, 0, 255, 128)); // semi-transparent blue
                        g.fillRect(x, y, CELL_SIZE, CELL_SIZE);
                        g.setColor(Color.BLACK);
                        g.drawRect(x, y, CELL_SIZE, CELL_SIZE);
                    }
                }
            }
        }
    }
}
