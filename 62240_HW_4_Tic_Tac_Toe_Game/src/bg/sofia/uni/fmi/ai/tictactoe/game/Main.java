package bg.sofia.uni.fmi.ai.tictactoe.game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("Do you want to be first?");
            System.out.println("1. Yes");
            System.out.println("2. No");
            System.out.println("Your choice is: ");
            choice = scanner.nextInt();
        } while (!isValidChoice(choice));

        final boolean isUserFirst = choice == 1;
        final TicTacToeGame game = new TicTacToeGame();
        game.play(isUserFirst);
    }

    private static boolean isValidChoice(final int choice) {
        if (choice < 1 || choice > 2) {
            System.out.println("Invalid choice!");
            return false;
        }
        return true;
    }
}
