package me.example;
import java.util.Map;
import java.awt.Color;
import java.util.List;
import java.util.Arrays;

public class Block {
    private int[][] shape;
    private int orientation;
    private int x;
    private int y;
    private Color color = null;
    private boolean placed = false;

    public static final Map<String, int[][]> BLOCKS = Map.of(
            "1x5", new int[][]{{1},{1},{1},{1},{1}},
            "1x4", new int[][]{{1},{1},{1},{1}},
            "2x2", new int[][]{{1,1},{1,1}},
            "2x3", new int[][]{{1,1,1},{1,1,1}},
            "3x3", new int[][]{{1,1,1},{1,1,1},{1,1,1}},
            "T", new int[][]{{1,0},{1,1}, {1,0}},
            "Z", new int[][]{{1,1,0},{0,1,1}},
            "L", new int[][]{{1,1,1},{1,0,0}},
            "Corner", new int[][]{{1,1,1},{1,0,0},{1,0,0}}
    );

    public Block(String shape, int orientation) {
        // Orientation is nx90 degrees
        // Orientation 1 = 90 degree rotation counter clockwise
        this.shape = BLOCKS.get(shape);
        for (int i = 0; i < orientation; i++) {
            this.shape = rotateCounterClockwise90(this.shape);
        }
        this.orientation = orientation;
        this.x = 0;
        this.y = 0;
    }

    public Block(String shape, int orientation, int row, int col) {
        // Orientation is nx90 degrees
        // Orientation 1 = 90 degree rotation counter clockwise
        this.shape = BLOCKS.get(shape);
        for (int i = 0; i < orientation; i++) {
            this.shape = rotateCounterClockwise90(this.shape);
        }
        this.orientation = orientation;
        this.x = col;
        this.y = row;
    }

    public int[][] getShape() {
        return shape;
    }

    public void setShape(int[][] shape) {
        this.shape = shape;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void move(int dx, int dy) {
        this.x += dx;
        this.y += dy;
    }

    @Override
    public String toString() {
        return Arrays.deepToString(this.shape) + " at (" + x + ", " + y + ")";
    }

    public static int[][] rotateCounterClockwise90(int[][] matrix) {
        int n = matrix.length;
        int m = matrix[0].length;
        int[][] rotated = new int[m][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                rotated[j][n - 1 - i] = matrix[i][j];
            }
        }
        return rotated;
    }

    public boolean isPlaced() {
        return placed;
    }
    public void setPlaced(boolean placed) {
        this.placed = placed;
    }

    public Color getColor() {
        return color;
    }
    public void setColor(Color color) {
        this.color = color;
    }
}
