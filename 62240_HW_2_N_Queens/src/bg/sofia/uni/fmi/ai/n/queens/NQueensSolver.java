package bg.sofia.uni.fmi.ai.n.queens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class NQueensSolver {
    private final int maxIteration;
    private final int numberOfQueens;
    private final int[] columns;
    private final int[] rowConflicts;
    private final int[] mainDiagonalConflicts;
    private final int[] reversedDiagonalConflicts;

    public NQueensSolver(final int numberOfQueens) {
        this.numberOfQueens = numberOfQueens;
        final int k = 4;
        maxIteration = k * numberOfQueens;
        this.rowConflicts = new int[this.numberOfQueens];
        this.columns = new int[this.numberOfQueens];
        this.mainDiagonalConflicts = new int[this.numberOfQueens * 2 - 1];
        this.reversedDiagonalConflicts = new int[this.numberOfQueens * 2 - 1];
        initializeBoard();
    }

    private void initializeBoard() {
        for (int i = 0; i < numberOfQueens * 2 - 1; i++) {
            mainDiagonalConflicts[i] = 0;
            reversedDiagonalConflicts[i] = 0;
            if (i < numberOfQueens) {
                rowConflicts[i] = 0;
            }
        }
        for (int i = 0; i < numberOfQueens; i++) {
            final int row = getRowWithMinConflict(i);
            this.columns[i] = row;
            rowConflicts[row] += 1;
            addConflictsToDiagonals(row, i);
        }
    }

    private void addConflictsToDiagonals(int row, int column) {
        mainDiagonalConflicts[(column - row) + numberOfQueens - 1] += 1;
        reversedDiagonalConflicts[column + row] += 1;
    }

    public void solve() {
        int i = 0;
        while (true) {
            final int column = getColWithQueenWithMaxConf();
            if (column == Integer.MIN_VALUE) {
                break;
            }
            final int previousRow = columns[column];
            final int row = getRowWithMinConflict(column);
            columns[column] = row;
            recalculateConflicts(row, column, previousRow);
            i++;
            if (i == maxIteration && !hasConflicts()) {
                i = 0;
                initializeBoard();
            }
        }
    }

    private boolean hasConflicts() {
        return Collections.singletonList(rowConflicts)
                .containsAll(Collections.singletonList(1)) && Collections.singletonList(mainDiagonalConflicts)
                .containsAll(Arrays.asList(0, 1)) && Collections.singletonList(reversedDiagonalConflicts)
                .containsAll(Arrays.asList(0, 1));
    }

    private int getColWithQueenWithMaxConf() {
        final List<Integer> queensWithMaxConflicts = new ArrayList<>();
        int maxConflicts = 0;

        for (int i = 0; i < numberOfQueens; i++) {
            final int row = columns[i];
            final int mainDiagonalIndex = i - row + numberOfQueens - 1;
            final int reversedDiagonalIndex = i + row;
            final int conflicts =
                    rowConflicts[row] + mainDiagonalConflicts[mainDiagonalIndex] + reversedDiagonalConflicts[reversedDiagonalIndex] - 3;
            if (conflicts == maxConflicts) {
                queensWithMaxConflicts.add(i);
            } else if (conflicts > maxConflicts) {
                maxConflicts = conflicts;
                queensWithMaxConflicts.clear();
                queensWithMaxConflicts.add(i);
            }
        }
        if (maxConflicts == 0) {
            return Integer.MIN_VALUE;
        }

        return queensWithMaxConflicts.get(ThreadLocalRandom.current()
                .nextInt(queensWithMaxConflicts.size()));
    }

    private int getRowWithMinConflict(final int column) {
        final List<Integer> queensWithMinConflicts = new ArrayList<>();
        int minConflicts = numberOfQueens;
        for (int i = 0; i < numberOfQueens; i++) {
            final int row = columns[column];
            if (i != row) {
                final int mainDiagonalIndex = column - i + numberOfQueens - 1;
                final int reversedDiagonalIndex = column + i;
                final int conflicts =
                        rowConflicts[i] + mainDiagonalConflicts[mainDiagonalIndex] + reversedDiagonalConflicts[reversedDiagonalIndex];
                if (conflicts == minConflicts) {
                    queensWithMinConflicts.add(i);
                } else if (conflicts < minConflicts) {
                    minConflicts = conflicts;
                    queensWithMinConflicts.clear();
                    queensWithMinConflicts.add(i);
                }
            }
        }

        return queensWithMinConflicts.isEmpty() ? 0 : queensWithMinConflicts.get(ThreadLocalRandom.current()
                .nextInt(queensWithMinConflicts.size()));
    }

    private void recalculateConflicts(final int row, final int column, final int previousRow) {
        rowConflicts[previousRow] = rowConflicts[previousRow] - 1;
        rowConflicts[row] = rowConflicts[row] + 1;

        mainDiagonalConflicts[column - previousRow + numberOfQueens - 1] =
                mainDiagonalConflicts[column - previousRow + numberOfQueens - 1] - 1;
        mainDiagonalConflicts[column - row + numberOfQueens - 1] = mainDiagonalConflicts[column - row + numberOfQueens - 1] + 1;

        reversedDiagonalConflicts[column + previousRow] = reversedDiagonalConflicts[column + previousRow] - 1;
        reversedDiagonalConflicts[column + row] = reversedDiagonalConflicts[column + row] + 1;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        for (int i = 0; i < numberOfQueens; i++) {
            for (int j = 0; j < numberOfQueens; j++) {
                final boolean isPositionOccupiedByQueen = columns[j] == i;
                sb.append(isPositionOccupiedByQueen ? "*\t" : "_\t");
            }
            sb.append('\n');
        }
        return sb.toString();
    }
}
