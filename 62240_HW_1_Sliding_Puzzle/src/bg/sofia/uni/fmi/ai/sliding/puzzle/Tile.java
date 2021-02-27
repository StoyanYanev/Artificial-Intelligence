package bg.sofia.uni.fmi.ai.sliding.puzzle;

import java.util.Objects;

public class Tile {
    private final int x;
    private final int y;
    private final int currentValue;
    private final int targetValue;

    public Tile(final int x, final int y, final int currentValue, final int targetValue) {
        this.x = x;
        this.y = y;
        this.currentValue = currentValue;
        this.targetValue = targetValue;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getCurrentValue() {
        return currentValue;
    }

    public int getTargetValue() {
        return targetValue;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final Tile tile = (Tile) o;
        return x == tile.x && y == tile.y && currentValue == tile.currentValue && targetValue == tile.targetValue;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, currentValue, targetValue);
    }
}
