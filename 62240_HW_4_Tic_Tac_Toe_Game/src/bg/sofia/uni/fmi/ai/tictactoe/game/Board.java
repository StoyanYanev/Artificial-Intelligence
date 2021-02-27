package bg.sofia.uni.fmi.ai.tictactoe.game;

import java.util.ArrayList;
import java.util.List;

public class Board {
    private final PlayerSymbol[][] states;
    private int moveCount;
    private boolean isGameOver;
    private PlayerSymbol winner;
    private PlayerSymbol playersTurn;

    public Board() {
        this.states = new PlayerSymbol[TicTacToeGameUtils.BOARD_SIZE][TicTacToeGameUtils.BOARD_SIZE];
        for (int i = 0; i < states.length; i++) {
            for (int j = 0; j < states.length; j++) {
                states[i][j] = PlayerSymbol.BLANK;
            }
        }
        moveCount = 0;
        winner = PlayerSymbol.BLANK;
        playersTurn = PlayerSymbol.X;
        isGameOver = false;
    }

    public Board(final Board board) {
        this.states = new PlayerSymbol[TicTacToeGameUtils.BOARD_SIZE][TicTacToeGameUtils.BOARD_SIZE];
        for (int i = 0; i < states.length; i++) {
            for (int j = 0; j < states.length; j++) {
                states[i][j] = board.getState(i, j);
            }
        }
        moveCount = board.getMoveCount();
        winner = board.getWinner();
        playersTurn = board.getPlayersTurn();
        isGameOver = board.isGameOver();
    }

    public PlayerSymbol getState(final int x, final int y) {
        return states[x][y];
    }

    public int getMoveCount() {
        return moveCount;
    }

    public boolean isGameOver() {
        return isGameOver;
    }

    public PlayerSymbol getWinner() {
        return winner;
    }

    public PlayerSymbol getPlayersTurn() {
        return playersTurn;
    }

    public void moveOnPosition(final Position position) {
        if (isGameOver()) {
            return;
        }

        this.states[position.getX()][position.getY()] = playersTurn;

        moveCount++;
        if (moveCount == TicTacToeGameUtils.MAX_NUMBER_OF_MOVES) {
            winner = PlayerSymbol.BLANK;
            isGameOver = true;
        }

        checkRow(position.getX());
        checkColumn(position.getY());
        checkMainDiagonal(position.getX(), position.getY());
        checkReversedDiagonal(position.getX(), position.getY());
        playersTurn = (playersTurn == PlayerSymbol.X) ? PlayerSymbol.O : PlayerSymbol.X;
    }

    public List<Position> getPossibleMoves() {
        final List<Position> positions = new ArrayList<>();
        for (int i = 0; i < states.length; i++) {
            for (int j = 0; j < states.length; j++) {
                if (states[i][j] == PlayerSymbol.BLANK) {
                    positions.add(new Position(i, j));
                }
            }
        }

        return positions;
    }

    private void checkRow(int row) {
        for (int i = 1; i < TicTacToeGameUtils.BOARD_SIZE; i++) {
            if (states[row][i] != states[row][i - 1]) {
                break;
            }
            if (i == TicTacToeGameUtils.BOARD_SIZE - 1) {
                winner = playersTurn;
                isGameOver = true;
                break;
            }
        }
    }

    private void checkColumn(int column) {
        for (int i = 1; i < TicTacToeGameUtils.BOARD_SIZE; i++) {
            if (states[i][column] != states[i - 1][column]) {
                break;
            } else if (i == TicTacToeGameUtils.BOARD_SIZE - 1) {
                winner = playersTurn;
                isGameOver = true;
                break;
            }
        }
    }

    private void checkMainDiagonal(int x, int y) {
        if (x == y) {
            for (int i = 1; i < TicTacToeGameUtils.BOARD_SIZE; i++) {
                if (states[i][i] != states[i - 1][i - 1]) {
                    break;
                } else if (i == TicTacToeGameUtils.BOARD_SIZE - 1) {
                    winner = playersTurn;
                    isGameOver = true;
                    break;
                }
            }
        }
    }

    private void checkReversedDiagonal(int x, int y) {
        if (TicTacToeGameUtils.BOARD_SIZE - 1 - x == y) {
            for (int i = 1; i < TicTacToeGameUtils.BOARD_SIZE; i++) {
                if (states[TicTacToeGameUtils.BOARD_SIZE - 1 - i][i] != states[TicTacToeGameUtils.BOARD_SIZE - i][i - 1]) {
                    break;
                } else if (i == TicTacToeGameUtils.BOARD_SIZE - 1) {
                    winner = playersTurn;
                    isGameOver = true;
                    break;
                }
            }
        }
    }

    @Override
    public String toString() {
        final StringBuilder builder = new StringBuilder();
        for (int i = 0; i < states.length; i++) {
            for (int j = 0; j < states.length; j++) {
                builder.append(states[i][j].getSymbol());
                builder.append(" ");
            }
            if (i != states.length - 1) {
                builder.append("\n");
            }
        }

        return builder.toString();
    }
}
