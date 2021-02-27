package bg.sofia.uni.fmi.ai.tictactoe.game;

public final class TicTacToeGameUtils {

    private TicTacToeGameUtils() {
        throw new IllegalArgumentException("The class can no be initialize!");
    }

    public static final int BOARD_SIZE = 3;
    public static final int MAX_DEPTH = 10;
    public static final int MAX_NUMBER_OF_MOVES = BOARD_SIZE * BOARD_SIZE;
}
