package me.example;

import javax.swing.JPanel;

import me.example.Block;
import me.example.Grid;

import java.awt.Graphics;
import java.awt.Color;
import java.util.List;
import java.awt.Point;

public class BlockUI extends JPanel{
    private List<Block> blocks;
    public static final int BLOCK_CELL_SIZE = 16;
    public static final int PADDING = 32;

    private Block draggedBlock = null;
    private Point dragLocation = null;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;

    public BlockUI(List<Block> blocks) {
        this.blocks = blocks;
        // Set preferred width to match the grid (Grid.SIZE * BLOCK_CELL_SIZE)
        setPreferredSize(new java.awt.Dimension(Grid.SIZE * 32, BLOCK_CELL_SIZE * 5));
    }

    public void setBlocks(List<Block> blocks) {
        this.blocks = blocks;
        repaint();
    }

    public void setDraggedBlock(Block block, Point location, int offsetX, int offsetY) {
        this.draggedBlock = block;
        this.dragLocation = location;
        this.dragOffsetX = offsetX;
        this.dragOffsetY = offsetY;
        repaint();
    }

    public void clearDraggedBlock() {
        this.draggedBlock = null;
        this.dragLocation = null;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        // Draw blocks in their normal positions
        if (blocks == null) return;
        int totalWidth = Grid.SIZE * 32;
        int blockAreaWidth = totalWidth / 3;
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            if (block == draggedBlock) continue; // Don't draw the dragged block in its original spot
            int[][] shape = block.getShape();
            int rows = shape.length;
            int cols = shape[0].length;
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
        // Draw the dragged block following the mouse
        if (draggedBlock != null && dragLocation != null) {
            int[][] shape = draggedBlock.getShape();
            int rows = shape.length;
            int cols = shape[0].length;
            int drawX = dragLocation.x - dragOffsetX;
            int drawY = dragLocation.y - dragOffsetY;
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (shape[r][c] == 1) {
                        g.setColor(new Color(255, 200, 0, 180)); // semi-transparent
                        g.fillRect(drawX + c * BLOCK_CELL_SIZE, drawY + r * BLOCK_CELL_SIZE, BLOCK_CELL_SIZE, BLOCK_CELL_SIZE);
                        g.setColor(Color.BLACK);
                        g.drawRect(drawX + c * BLOCK_CELL_SIZE, drawY + r * BLOCK_CELL_SIZE, BLOCK_CELL_SIZE, BLOCK_CELL_SIZE);
                    }
                }
            }
        }
    }
}
