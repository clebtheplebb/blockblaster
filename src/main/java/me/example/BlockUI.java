package me.example;

import javax.swing.JPanel;
import java.awt.Graphics;
import java.awt.Color;
import java.util.List;

public class BlockUI extends JPanel{
    private List<Block> blocks;
    private static final int BLOCK_CELL_SIZE = 16;
    private static final int PADDING = 32;

    public BlockUI(List<Block> blocks) {
        this.blocks = blocks;
        // Set preferred width to match the grid (Grid.SIZE * BLOCK_CELL_SIZE)
        setPreferredSize(new java.awt.Dimension(Grid.SIZE * 32, BLOCK_CELL_SIZE * 5));
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (blocks == null) return;
        int totalWidth = Grid.SIZE * 32;
        int blockAreaWidth = totalWidth / 3;
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            int[][] shape = block.getShape();
            int rows = shape.length;
            int cols = shape[0].length;

            // Center block in its third of the grid width
            int offsetX = i * blockAreaWidth + (blockAreaWidth - cols * BLOCK_CELL_SIZE) / 2 + PADDING;
            int offsetY = (BLOCK_CELL_SIZE * (5 - rows)) / 2;  
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (shape[r][c] == 1) {
                        g.setColor(Color.ORANGE);
                        g.fillRect(offsetX + c * BLOCK_CELL_SIZE, offsetY + r * BLOCK_CELL_SIZE, BLOCK_CELL_SIZE, BLOCK_CELL_SIZE);
                        g.setColor(Color.BLACK);
                        g.drawRect(offsetX + c * BLOCK_CELL_SIZE, offsetY + r * BLOCK_CELL_SIZE, BLOCK_CELL_SIZE, BLOCK_CELL_SIZE);
                    }
                }
            }
        }
    }
}
