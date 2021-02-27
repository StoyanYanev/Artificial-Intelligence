package bg.sofia.uni.fmi.ai.tictactoe.game;

import java.util.List;
import java.util.Scanner;

public class TicTacToeGame {
    private final Board board;
    private PlayerSymbol userPlayerSymbol;
    private PlayerSymbol computerPlayerSymbol;
    private final Scanner scanner;
    private boolean isUserTurn;

    public TicTacToeGame() {
        this.board = new Board();
        this.scanner = new Scanner(System.in);
    }

    public void play(final boolean isUserFirst) {
        initializePlayers(isUserFirst);
        do {
            if (isUserTurn) {
                System.out.println("It is your turn");
                final Position position = getEnteredPosition();
                board.moveOnPosition(position);
                isUserTurn = false;
            } else {
                System.out.println("It is computer turn");
                minMax(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE);
                isUserTurn = true;
            }
            System.out.println(board);
            System.out.println();
        } while (!board.isGameOver());
        printWinner();
    }

    private void initializePlayers(final boolean isUserFirst) {
        userPlayerSymbol = isUserFirst ? PlayerSymbol.X : PlayerSymbol.O;
        computerPlayerSymbol = !isUserFirst ? PlayerSymbol.X : PlayerSymbol.O;
        isUserTurn = isUserFirst;
    }

    private Position getEnteredPosition() {
        int x;
        int y;
        do {
            System.out.println("Enter position [1, 3]: ");
            x = scanner.nextInt() - 1;
            y = scanner.nextInt() - 1;
        } while (!isValidPosition(x, y));

        return new Position(x, y);
    }

    private boolean isValidPosition(final int x, final int y) {
        final boolean isValidPosition = x >= 0 && y >= 0 && x <= TicTacToeGameUtils.BOARD_SIZE && y <= TicTacToeGameUtils.BOARD_SIZE &&
                board.getState(x, y) == PlayerSymbol.BLANK;
        if (!isValidPosition) {
            System.out.println("Invalid position!");
        }

        return isValidPosition;
    }

    private void printWinner() {
        if (board.getWinner() == PlayerSymbol.BLANK) {
            System.out.println("The game is Draw!");
        } else if (board.getWinner() == userPlayerSymbol) {
            System.out.println("You win the game");
        } else {
            System.out.println("You lost the game");
        }
    }

    private int minMax(Board board, int depth, int alpha, int beta) {
        if (depth == TicTacToeGameUtils.MAX_DEPTH || board.isGameOver()) {
            if (board.getWinner() == computerPlayerSymbol) {
                return TicTacToeGameUtils.MAX_DEPTH - depth;
            } else if (board.getWinner() == userPlayerSymbol) {
                return depth - TicTacToeGameUtils.MAX_DEPTH;
            }
            return 0;
        }
        if (board.getPlayersTurn() == computerPlayerSymbol) {
            return maxValue(board, depth, alpha, beta);
        } else {
            return minValue(board, depth, alpha, beta);
        }
    }

    private int minValue(Board board, int depth, int alpha, int beta) {
        final List<Position> possibleMoves = board.getPossibleMoves();
        Position positionToMove = null;
        for (Position position : possibleMoves) {
            Board newBoard = new Board(board);
            newBoard.moveOnPosition(position);

            int result = minMax(newBoard, depth + 1, alpha, beta);
            if (result < beta) {
                beta = result;
                positionToMove = position;
            }
            if (alpha >= beta) {
                break;
            }
        }
        if (positionToMove != null) {
            board.moveOnPosition(positionToMove);
        }
        return beta;
    }

    private int maxValue(Board board, int depth, int alpha, int beta) {
        final List<Position> possibleMoves = board.getPossibleMoves();
        Position positionToMove = null;
        for (Position position : possibleMoves) {
            Board newBoard = new Board(board);
            newBoard.moveOnPosition(position);

            int result = minMax(newBoard, depth + 1, alpha, beta);
            if (result > alpha) {
                alpha = result;
                positionToMove = position;
            }
            if (alpha >= beta) {
                break;
            }
        }
        if (positionToMove != null) {
            board.moveOnPosition(positionToMove);
        }
        return alpha;
    }
}
