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
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // if (draggedBlock != null && dragLocation != null) {
        //     // Convert dragLocation to grid coordinates and attempt to place block
        //     Point gridPoint = SwingUtilities.convertPoint(blockUI, dragLocation, gridUI);
        //     gridUI.tryPlaceBlock(draggedBlock, gridPoint.x, gridPoint.y);
        //     // Remove block from BlockUI
        //     blocks.remove(draggedBlockIndex);
        //     blockUI.setBlocks(blocks);
        // }
        blockUI.clearDraggedBlock();
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
