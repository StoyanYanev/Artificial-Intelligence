package bg.sofia.uni.fmi.ai.travelling.salesman.problem;

import java.util.Objects;

public class City {
    private final int x;
    private final int y;

    /**
     * Constructor city with random (x,y) coordinates in a 300 unit grid
     */
    public City() {
        this.x = (int) (Math.random() * TravelingSalesmanProblemUtils.GRID_SIZE);
        this.y = (int) (Math.random() * TravelingSalesmanProblemUtils.GRID_SIZE);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public double distanceTo(final City city) {
        final int xDistance = Math.abs(this.getX() - city.getX());
        final int yDistance = Math.abs(this.getY() - city.getY());

        return Math.sqrt(Math.pow(xDistance, 2) + Math.pow(yDistance, 2));
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("City{");
        sb.append("x=")
                .append(x);
        sb.append(", y=")
                .append(y);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        final City city = (City) o;
        return x == city.x && y == city.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
