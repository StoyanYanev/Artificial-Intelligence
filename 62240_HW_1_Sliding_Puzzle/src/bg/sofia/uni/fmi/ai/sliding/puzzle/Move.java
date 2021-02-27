package bg.sofia.uni.fmi.ai.sliding.puzzle;

public enum Move {
    LEFT("left"), RIGHT("right"), UP("up"), DOWN("down");

    private final String moveName;
    private Move opposite;

    Move(final String moveName) {
        this.moveName = moveName;
    }

    public String getMoveName() {
        return moveName;
    }

    public Move getOpposite() {
        return opposite;
    }

    static {
        LEFT.opposite = RIGHT;
        RIGHT.opposite = LEFT;
        UP.opposite = DOWN;
        DOWN.opposite = UP;
    }
}
