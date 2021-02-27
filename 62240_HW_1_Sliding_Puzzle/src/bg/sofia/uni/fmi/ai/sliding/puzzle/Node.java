package bg.sofia.uni.fmi.ai.sliding.puzzle;

import java.util.Arrays;
import java.util.Objects;

public class Node {
    private Tile[][] grid;
    private Node previousNode;
    private Move move;

    public Node(final Tile[][] grid) {
        this.grid = grid;
        this.previousNode = null;
    }

    public Tile[][] getGrid() {
        return grid;
    }

    public void setGrid(final Tile[][] grid) {
        this.grid = grid;
    }

    public Node getPreviousNode() {
        return previousNode;
    }

    public void setPreviousNode(final Node previousNode) {
        this.previousNode = previousNode;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(final Move move) {
        this.move = move;
    }

    public boolean areTargetValuesReached() {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j].getTargetValue() != grid[i][j].getCurrentValue()) {
                    return false;
                }
            }
        }
        return true;
    }

    public Integer calculateManhattanDistances() {
        int distance = 0;
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                final Tile targetTile = getTileByTargetValue(grid[i][j].getTargetValue());
                if (targetTile != null && targetTile.getCurrentValue() != 0) {
                    distance += Math.abs(grid[i][j].getX() - targetTile.getX()) + Math.abs(grid[i][j].getY() - targetTile.getY());
                }
            }
        }
        return distance;
    }

    public Tile getTileByTargetValue(final int targetValue) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j].getCurrentValue() == targetValue) {
                    return grid[i][j];
                }
            }
        }
        return null;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Node node = (Node) o;
        if (grid.length != node.grid.length) {
            return false;
        }

        for (int row = 0; row < node.grid.length; row++) {
            if (!Arrays.equals(grid[row], node.grid[row])) {
                return false;
            }
        }
        return true;
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(previousNode, move);
        result = 31 * result + Arrays.hashCode(grid);
        return result;
    }
}
