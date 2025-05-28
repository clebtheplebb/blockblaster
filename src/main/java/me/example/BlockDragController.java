package me.example;

import me.example.Block;
import me.example.BlockUI;
import me.example.GridUI;
import me.example.Grid;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

/**
 * Handles mouse events for dragging blocks from BlockUI onto the grid.
 */
public class BlockDragController extends MouseAdapter implements MouseMotionListener {
    private final BlockUI blockUI;
    private final GridUI gridUI;
    private final List<Block> blocks;
    private Block draggedBlock = null;
    private int dragOffsetX, dragOffsetY;
    private int draggedBlockIndex = -1;
    private Point dragLocation = null;

    public BlockDragController(BlockUI blockUI, GridUI gridUI, List<Block> blocks) {
        this.blockUI = blockUI;
        this.gridUI = gridUI;
        this.blocks = blocks;
        blockUI.addMouseListener(this);
        blockUI.addMouseMotionListener(this);
    }

    @Override
    public void mousePressed(MouseEvent e) {
        int x = e.getX();
        int y = e.getY();
        int totalWidth = Grid.SIZE * 32;
        int blockAreaWidth = totalWidth / 3;
        for (int i = 0; i < blocks.size(); i++) {
            Block block = blocks.get(i);
            int[][] shape = block.getShape();
            int rows = shape.length;
            int cols = shape[0].length;
            int offsetX = i * blockAreaWidth + (blockAreaWidth - cols * BlockUI.BLOCK_CELL_SIZE) / 2 + BlockUI.PADDING;
            int offsetY = (BlockUI.BLOCK_CELL_SIZE * (5 - rows)) / 2;
            Rectangle blockRect = new Rectangle(offsetX, offsetY, cols * BlockUI.BLOCK_CELL_SIZE, rows * BlockUI.BLOCK_CELL_SIZE);
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
            blockUI.setDraggedBlock(draggedBlock, dragLocation, dragOffsetX, dragOffsetY);
            blockUI.repaint();
            // Convert mouse position to grid coordinates relative to gridUI
            Point gridPoint = SwingUtilities.convertPoint(blockUI, dragLocation, gridUI);
            int gridX = (gridPoint.x - 32) / 32;
            int gridY = (gridPoint.y - 32) / 32;
            boolean fits = false;
            if (draggedBlock != null) {
                int[][] shape = draggedBlock.getShape();
                int shapeRows = shape.length;
                int shapeCols = shape[0].length;
                if (gridX >= 0 && gridY >= 0 &&
                    gridX + shapeCols <= Grid.SIZE &&
                    gridY + shapeRows <= Grid.SIZE) {
                    fits = true;
                }
            }
            if (fits) {
                gridUI.setPreviewBlock(draggedBlock, gridX, gridY);
            } else {
                gridUI.clearPreviewBlock();
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (draggedBlock != null && dragLocation != null) {
            // Convert dragLocation to grid coordinates relative to gridUI
            Point gridPoint = SwingUtilities.convertPoint(blockUI, dragLocation, gridUI);
            int gridX = (gridPoint.x - 32) / 32;
            int gridY = (gridPoint.y - 32) / 32;
            int[][] shape = draggedBlock.getShape();
            int shapeRows = shape.length;
            int shapeCols = shape[0].length;
            // Only place if the block fits entirely in the grid
            if (gridX >= 0 && gridY >= 0 &&
                gridX + shapeCols <= Grid.SIZE &&
                gridY + shapeRows <= Grid.SIZE) {
                int[][] board = gridUI.getGrid().getBoard();
                for (int i = 0; i < shapeRows; i++) {
                    for (int j = 0; j < shapeCols; j++) {
                        if (shape[i][j] != 0) {
                            board[gridY + i][gridX + j] = 2; // 2 = yellow
                        }
                    }
                }
                // Clear full rows
                for (int i = 0; i < Grid.SIZE; i++) {
                    boolean fullRow = true;
                    for (int j = 0; j < Grid.SIZE; j++) {
                        if (board[i][j] == 0) {
                            fullRow = false;
                            break;
                        }
                    }
                    if (fullRow) {
                        for (int j = 0; j < Grid.SIZE; j++) {
                            board[i][j] = 0;
                        }
                    }
                }
                // Clear full columns
                for (int j = 0; j < Grid.SIZE; j++) {
                    boolean fullCol = true;
                    for (int i = 0; i < Grid.SIZE; i++) {
                        if (board[i][j] == 0) {
                            fullCol = false;
                            break;
                        }
                    }
                    if (fullCol) {
                        for (int i = 0; i < Grid.SIZE; i++) {
                            board[i][j] = 0;
                        }
                    }
                }
                gridUI.repaint();
            }
        }
        blockUI.clearDraggedBlock();
        gridUI.clearPreviewBlock();
        draggedBlock = null;
        dragLocation = null;
        draggedBlockIndex = -1;
    }

    @Override
    public void mouseMoved(MouseEvent e) {}

    // Optionally, add a method in BlockUI to draw the dragged block following the mouse
    public Block getDraggedBlock() { return draggedBlock; }
    public Point getDragLocation() { return dragLocation; }
}
