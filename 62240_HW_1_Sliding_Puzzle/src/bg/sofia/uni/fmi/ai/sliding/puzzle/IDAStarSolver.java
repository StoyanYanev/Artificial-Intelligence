package bg.sofia.uni.fmi.ai.sliding.puzzle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class IDAStarSolver {
    private final Node initialState;

    public IDAStarSolver(final Node initialState) {
        this.initialState = initialState;
    }

    public Node solve() {
        final List<Node> path = new ArrayList<>();
        path.add(0, initialState);
        int threshold = initialState.calculateManhattanDistances();
        int result;

        do {
            result = idaStar(path, 0, threshold);

            if (result == 0) {
                return path.get(path.size() - 1);
            }

            threshold = result;
        } while (threshold != Integer.MAX_VALUE);

        return null;
    }

    private int idaStar(final List<Node> currentPath, final int currentCost, final int threshold) {
        final Node node = currentPath.get(currentPath.size() - 1);
        final int estimatedCost = currentCost + node.calculateManhattanDistances();
        if (estimatedCost > threshold) {
            return estimatedCost;
        }
        if (node.areTargetValuesReached()) {
            return 0;
        }

        int minimumCost = Integer.MAX_VALUE;
        final List<Node> successors = getSuccessors(node);
        for (final Node successor : successors) {
            if (!currentPath.contains(successor)) {
                currentPath.add(successor);
                final int result = idaStar(currentPath, currentCost + 1, threshold);
                if (result == 0) {
                    return 0;
                }
                if (result < minimumCost) {
                    minimumCost = result;
                }

                currentPath.remove(currentPath.size() - 1);
            }
        }
        return minimumCost;
    }

    public List<Node> getSuccessors(final Node node) {
        final List<Node> successors = new ArrayList<>();
        final Tile blankTile = getBlankTile(node.getGrid());
        if (canMoveUp(blankTile)) {
            successors.add(createChild(blankTile, node, Move.UP));
        }
        if (canMoveDown(blankTile)) {
            successors.add(createChild(blankTile, node, Move.DOWN));
        }
        if (canMoveRight(blankTile)) {
            successors.add(createChild(blankTile, node, Move.RIGHT));
        }
        if (canMoveLeft(blankTile)) {
            successors.add(createChild(blankTile, node, Move.LEFT));
        }

        return successors;
    }

    private boolean canMoveUp(final Tile blankTile) {
        return blankTile.getX() - 1 >= 0;
    }

    private boolean canMoveDown(final Tile blankTile) {
        return blankTile.getX() + 1 <= initialState.getGrid().length - 1;
    }

    private boolean canMoveRight(final Tile blankTile) {
        return blankTile.getY() + 1 <= initialState.getGrid().length - 1;
    }

    private boolean canMoveLeft(final Tile blankTile) {
        return blankTile.getY() - 1 >= 0;
    }

    private Node createChild(final Tile blankTile, final Node node, final Move move) {
        final Tile[][] copy = Arrays.stream(node.getGrid())
                .map(Tile[]::clone)
                .toArray(Tile[][]::new);
        moveTile(blankTile, copy, move);

        final Node childNode = new Node(copy);
        childNode.setMove(move);
        childNode.setPreviousNode(node);

        return childNode;
    }

    private Tile getBlankTile(final Tile[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                if (grid[i][j].getCurrentValue() == 0) {
                    return grid[i][j];
                }
            }
        }

        return null;
    }

    public void moveTile(final Tile blankTile, final Tile[][] grid, final Move move) {
        final Tile tileToMove;
        switch (move) {
            case UP:
                tileToMove = grid[blankTile.getX() - 1][blankTile.getY()];
                break;
            case DOWN:
                tileToMove = grid[blankTile.getX() + 1][blankTile.getY()];
                break;
            case LEFT:
                tileToMove = grid[blankTile.getX()][blankTile.getY() - 1];
                break;
            case RIGHT:
                tileToMove = grid[blankTile.getX()][blankTile.getY() + 1];
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + move);
        }
        grid[tileToMove.getX()][tileToMove.getY()] =
                new Tile(tileToMove.getX(), tileToMove.getY(), blankTile.getCurrentValue(), tileToMove.getTargetValue());
        grid[blankTile.getX()][blankTile.getY()] =
                new Tile(blankTile.getX(), blankTile.getY(), tileToMove.getCurrentValue(), blankTile.getTargetValue());
    }
}
