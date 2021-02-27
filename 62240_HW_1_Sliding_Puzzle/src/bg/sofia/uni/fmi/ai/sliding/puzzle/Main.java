package bg.sofia.uni.fmi.ai.sliding.puzzle;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        int size;
        do {
            System.out.println("Enter the size of the initial board: ");
            size = scanner.nextInt();
        } while (!isValidSize(size));

        int zeroPosition;
        do {
            System.out.println("Enter the position of 0: ");
            zeroPosition = scanner.nextInt();
        } while (!isValidZeroPosition(zeroPosition));

        scanner.nextLine();

        if (zeroPosition == -1) {
            zeroPosition = size;
        }
        final int sizeOfBord = (int) Math.sqrt(size + 1);
        final Tile[][] puzzle = new Tile[sizeOfBord][sizeOfBord];
        int counter = 1;
        int temp = 1;
        for (int i = 0; i < sizeOfBord; i++) {
            final String line = scanner.nextLine();
            final String[] row = line.split(" ");
            for (int j = 0; j < row.length; j++) {
                final int value = Integer.parseInt(row[j]);
                if (temp == zeroPosition + 1) {
                    puzzle[i][j] = new Tile(i, j, value, 0);
                    temp++;
                } else {
                    puzzle[i][j] = new Tile(i, j, value, counter);
                    counter++;
                    temp++;
                }
            }
        }

        final IDAStarSolver solver = new IDAStarSolver(new Node(puzzle));
        final long startTime = System.currentTimeMillis();
        final Node n = solver.solve();
        final long endTime = System.currentTimeMillis();
        final double solutionTime = ((double) (endTime - startTime)) / 1000;
        System.out.println(String.format("Solution found in %.3f s", solutionTime));
        printPath(n);
    }

    private static boolean isValidSize(final int sizeOfBord) {
        if (sizeOfBord < 0) {
            System.out.println("The size of the initial board can not be negative!");
            return false;
        } else if (((sizeOfBord + 1) % (int) Math.sqrt(sizeOfBord + 1)) != 0) {
            System.out.println("Invalid input size!");
            return false;
        }
        return true;
    }

    private static boolean isValidZeroPosition(final int zeroPosition) {
        if (zeroPosition < IDAStarSolverUtils.MIN_ZERO_POSITION) {
            System.out.println("Invalid zero position!");
            return false;
        }
        return true;
    }

    private static void printPath(Node node) {
        final List<Node> paths = new ArrayList<>();
        paths.add(node);
        while (node.getPreviousNode() != null) {
            paths.add(0, node.getPreviousNode());
            node = node.getPreviousNode();
        }

        System.out.println(paths.size() - 1);
        paths.forEach(n -> {
            if (n.getMove() != null) {
                System.out.println(n.getMove()
                        .getOpposite()
                        .getMoveName());
            }
        });
    }
}