package bg.sofia.uni.fmi.ai.travelling.salesman.problem;

public final class TravelingSalesmanProblemUtils {
    private TravelingSalesmanProblemUtils() {
        throw new IllegalArgumentException("The class can not be initialize!");
    }

    public static final int MAX_NUMBER_OF_CITIES = 100;
    public static final int MIN_NUMBER_OF_CITIES = 1;
    public static final int GRID_SIZE = 400;
    public static final int TOURNAMENT_SIZE = 5;
    public static final int ELITISM = 1;
    public static final double MUTATION_RATE = 0.015;
    public static final int POPULATION_SIZE = 200;
    public static final int POPULATIONS = 150;

}
