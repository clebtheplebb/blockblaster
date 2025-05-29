package me.example;

import javax.swing.*;
import java.awt.*;
import java.util.List;

/**
 * Combines the grid and block UI into a single panel.
 */
public class GameUI extends JPanel {
    private Grid grid;
    public static final int GRID_CELL_SIZE = 32;
    public static final int BLOCK_CELL_SIZE = 16;
    public static final int PADDING = 32;

    // Drag-and-drop state
    private Block draggedBlock = null;
    private Point dragLocation = null;
    private int dragOffsetX = 0;
    private int dragOffsetY = 0;

    public GameUI(Grid grid) {
        this.grid = grid;
        setPreferredSize(new Dimension(Grid.SIZE * GRID_CELL_SIZE, GRID_CELL_SIZE * Grid.SIZE + BLOCK_CELL_SIZE * 5 + PADDING));
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

    // Helper to check if a block can be placed at a given grid position
    public boolean canPlaceBlock(Block block, int gridRow, int gridCol) {
        int[][] shape = block.getShape();
        int rows = shape.length;
        int cols = shape[0].length;
        int[][] board = grid.getBoard();
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                if (shape[r][c] == 1) {
                    int boardRow = gridRow + r;
                    int boardCol = gridCol + c;
                    if (boardRow < 0 || boardRow >= Grid.SIZE || boardCol < 0 || boardCol >= Grid.SIZE) return false;
                    if (board[boardRow][boardCol] != 0) return false;
                }
            }
        }
        return true;
    }

    // Helper to get the grid cell under the mouse (returns int[]{row, col} or null)
    public int[] getGridCellUnderMouse(Point mouse, int dragOffsetX, int dragOffsetY) {
        int x = mouse.x - dragOffsetX - 32;
        int y = mouse.y - dragOffsetY - 32;
        int col = x / GRID_CELL_SIZE;
        int row = y / GRID_CELL_SIZE;
        if (row >= 0 && row < Grid.SIZE && col >= 0 && col < Grid.SIZE) {
            return new int[]{row, col};
        }
        return null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        int[][] board = grid.getBoard();
        // Highlight preview if dragging
        if (draggedBlock != null && dragLocation != null) {
            int[] cell = getGridCellUnderMouse(dragLocation, dragOffsetX, dragOffsetY);
            if (cell != null && canPlaceBlock(draggedBlock, cell[0], cell[1])) {
                int[][] shape = draggedBlock.getShape();
                int rows = shape.length;
                int cols = shape[0].length;
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        if (shape[r][c] == 1) {
                            int gx = (cell[1] + c) * GRID_CELL_SIZE + 32;
                            int gy = (cell[0] + r) * GRID_CELL_SIZE + 32;
                            g.setColor(new Color(0, 255, 0, 100)); // semi-transparent green
                            g.fillRect(gx, gy, GRID_CELL_SIZE, GRID_CELL_SIZE);
                        }
                    }
                }
            }
        }
        // Draw grid
        for (int i = 0; i < Grid.SIZE; i++) {
            for (int j = 0; j < Grid.SIZE; j++) {
                if (board[i][j] == 0) {
                    g.setColor(Color.LIGHT_GRAY);
                } else {
                    g.setColor(Color.BLUE);
                }
                g.fillRect(j * GRID_CELL_SIZE + 32, i * GRID_CELL_SIZE + 32, GRID_CELL_SIZE, GRID_CELL_SIZE);
                g.setColor(Color.BLACK);
                g.drawRect(j * GRID_CELL_SIZE + 32, i * GRID_CELL_SIZE + 32, GRID_CELL_SIZE, GRID_CELL_SIZE);
            }
        }
        // Draw blocks in their normal positions (below the grid)
        if (GUI.currentBlocks == null) return;
        int totalWidth = Grid.SIZE * GRID_CELL_SIZE;
        int blockAreaWidth = totalWidth / 3;
        int blockYOffset = Grid.SIZE * GRID_CELL_SIZE + PADDING; // blocks below the grid
        for (int i = 0; i < GUI.currentBlocks.size(); i++) {
            Block block = GUI.currentBlocks.get(i);
            if (block == draggedBlock) continue; // Don't draw the dragged block in its original spot
            if (block.isPlaced()) continue; // Don't draw placed blocks
            int[][] shape = block.getShape();
            int rows = shape.length;
            int cols = shape[0].length;
            int offsetX = i * blockAreaWidth + (blockAreaWidth - cols * BLOCK_CELL_SIZE) / 2 + PADDING;
            int offsetY = blockYOffset + (BLOCK_CELL_SIZE * (5 - rows)) / 2;
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
        // Draw preview on grid if valid
        if (draggedBlock != null && dragLocation != null) {
            int[] cell = getGridCellUnderMouse(dragLocation, dragOffsetX, dragOffsetY);
            if (cell != null && canPlaceBlock(draggedBlock, cell[0], cell[1])) {
                int[][] shape = draggedBlock.getShape();
                int rows = shape.length;
                int cols = shape[0].length;
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        if (shape[r][c] == 1) {
                            int gx = (cell[1] + c) * GRID_CELL_SIZE + 32;
                            int gy = (cell[0] + r) * GRID_CELL_SIZE + 32;
                            g.setColor(new Color(0, 255, 0, 100)); // semi-transparent green
                            g.fillRect(gx, gy, GRID_CELL_SIZE, GRID_CELL_SIZE);
                            g.setColor(Color.BLACK);
                            g.drawRect(gx, gy, GRID_CELL_SIZE, GRID_CELL_SIZE);
                        }
                    }
                }
            }
        }
        // Always draw the dragged block following the mouse
        if (draggedBlock != null && dragLocation != null) {
            int[][] shape = draggedBlock.getShape();
            int rows = shape.length;
            int cols = shape[0].length;
            int drawX = dragLocation.x - dragOffsetX;
            int drawY = dragLocation.y - dragOffsetY;
            for (int r = 0; r < rows; r++) {
                for (int c = 0; c < cols; c++) {
                    if (shape[r][c] == 1) {
                        g.setColor(new Color(255, 200, 0)); // semi-transparent
                        g.fillRect(drawX + c * GRID_CELL_SIZE, drawY + r * GRID_CELL_SIZE, GRID_CELL_SIZE, GRID_CELL_SIZE);
                        g.setColor(Color.BLACK);
                        g.drawRect(drawX + c * GRID_CELL_SIZE, drawY + r * GRID_CELL_SIZE, GRID_CELL_SIZE, GRID_CELL_SIZE);
                    }
                }
            }
        }
    }

    public boolean placeBlockIfPossible() {
        if (draggedBlock != null && dragLocation != null) {
            int[] cell = getGridCellUnderMouse(dragLocation, dragOffsetX, dragOffsetY);
            if (cell != null && canPlaceBlock(draggedBlock, cell[0], cell[1])) {
                int[][] shape = draggedBlock.getShape();
                int rows = shape.length;
                int cols = shape[0].length;
                int[][] board = grid.getBoard();
                for (int r = 0; r < rows; r++) {
                    for (int c = 0; c < cols; c++) {
                        if (shape[r][c] == 1) {
                            board[cell[0] + r][cell[1] + c] = 1;
                        }
                    }
                }

                int[] rowTotals = new int[Grid.SIZE];
                int[] colTotals = new int[Grid.SIZE];

                // Calculate row and column totals
                for (int i = 0; i < Grid.SIZE; i++) {
                    for (int j = 0; j < Grid.SIZE; j++) {
                        rowTotals[i] += board[i][j];
                        colTotals[j] += board[i][j];
                    }
                }

                // Check for completed rows and columns
                for (int r = 0; r < Grid.SIZE; r++) {
                    if (rowTotals[r] == Grid.SIZE) {
                        // Clear the row
                        for (int j = 0; j < Grid.SIZE; j++) {
                            board[r][j] = 0;
                        }
                    }
                }

                for (int c = 0; c < Grid.SIZE; c++) {
                    if (colTotals[c] == Grid.SIZE) {
                        // Clear the column
                        for (int i = 0; i < Grid.SIZE; i++) {
                            board[i][c] = 0;
                        }
                    }
                }
                
                // Mark the placed block as placed instead of removing it
                draggedBlock.setPlaced(true);
                clearDraggedBlock();
                repaint();
                return true;
            }
        }
        clearDraggedBlock();
        repaint();
        return false;
    }
}
