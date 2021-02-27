package bg.sofia.uni.fmi.ai.tictactoe.game;

public enum PlayerSymbol {
    BLANK("_"), X("X"), O("O");

    private final String symbol;

    PlayerSymbol(final String symbol) {
        this.symbol = symbol;
    }

    public String getSymbol() {
        return symbol;
    }
}
