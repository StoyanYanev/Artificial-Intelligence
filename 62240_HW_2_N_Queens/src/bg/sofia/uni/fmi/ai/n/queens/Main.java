package bg.sofia.uni.fmi.ai.n.queens;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        int numberOfQueens;
        do {
            System.out.println("Enter the number of queens: ");
            numberOfQueens = scanner.nextInt();
        } while (!isValidNumberOfQueens(numberOfQueens) || !hasSolution(numberOfQueens));

        final long startTime = System.currentTimeMillis();
        final NQueensSolver nQueensSolver = new NQueensSolver(numberOfQueens);
        nQueensSolver.solve();
        final long endTime = System.currentTimeMillis();
        final double solutionTime = ((double) (endTime - startTime)) / 1000;
        System.out.println(String.format("Solution found in %.3f s", solutionTime));

        if (numberOfQueens <= NQueensSolverUtils.ALLOW_PRINT_SIZE) {
            System.out.println(nQueensSolver);
        }
    }

    private static boolean isValidNumberOfQueens(final int numberOfQueens) {
        if (numberOfQueens <= 0) {
            System.out.println("The number of the queens should be positive!");
            return false;
        }
        return true;
    }

    private static boolean hasSolution(final int numberOfQueens) {
        if (numberOfQueens == 2 || numberOfQueens == 3) {
            System.out.println("N Queens Problem does not have a solution for N=2, or N=3.");
            return false;
        }
        return true;
    }
}
