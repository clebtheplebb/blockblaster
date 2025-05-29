package me.example;

import me.example.Block;
import me.example.GameUI;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Handles mouse events for dragging blocks from BlockUI onto the grid.
 */
public class BlockDragController extends MouseAdapter {
    private final GameUI gameUI;
    private Block draggedBlock = null;
    private int dragOffsetX, dragOffsetY;
    private int draggedBlockIndex = -1;
    private Point dragLocation = null;

    public BlockDragController(GameUI gameUI) {
        this.gameUI = gameUI;
        gameUI.addMouseListener(this);
        gameUI.addMouseMotionListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int totalWidth = Grid.SIZE * 32;
        int blockAreaWidth = totalWidth / 3;
        for (int i = 0; i < GUI.currentBlocks.size(); i++) {
            Block block = GUI.currentBlocks.get(i);
            int[][] shape = block.getShape();
            int rows = shape.length;
            int cols = shape[0].length;
            int blockYOffset = Grid.SIZE * GameUI.GRID_CELL_SIZE + GameUI.PADDING; // blocks below the grid
            int offsetX = i * blockAreaWidth + (blockAreaWidth - cols * GameUI.BLOCK_CELL_SIZE) / 2 + GameUI.PADDING;
            int offsetY = blockYOffset + (GameUI.BLOCK_CELL_SIZE * (5 - rows)) / 2 + GameUI.PADDING / 2;
            Rectangle blockRect = new Rectangle(offsetX, offsetY, cols * GameUI.BLOCK_CELL_SIZE, rows * GameUI.BLOCK_CELL_SIZE);
            if (blockRect.contains(x, y)) {
                draggedBlock = block;
                draggedBlockIndex = i;
                dragOffsetX = x - offsetX;
                dragOffsetY = y - offsetY;
                dragLocation = e.getPoint();
                break;
            }
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (draggedBlock != null) {
            dragLocation = e.getPoint();
            gameUI.repaint();
            gameUI.setDraggedBlock(draggedBlock, dragLocation, dragOffsetX, dragOffsetY);
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        gameUI.placeBlockIfPossible();
        draggedBlock = null;
        dragLocation = null;
        draggedBlockIndex = -1;
    }

    // Optionally, add a method in BlockUI to draw the dragged block following the mouse
    public Block getDraggedBlock() { return draggedBlock; }
    public Point getDragLocation() { return dragLocation; }
}
