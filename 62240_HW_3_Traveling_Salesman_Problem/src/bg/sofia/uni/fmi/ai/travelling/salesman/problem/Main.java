package bg.sofia.uni.fmi.ai.travelling.salesman.problem;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        final Scanner scanner = new Scanner(System.in);
        int numberOfCities;
        do {
            System.out.println("Enter the number of cities:");
            numberOfCities = scanner.nextInt();
        } while (!isValidNumberOfCities(numberOfCities));

        final City[] cities = new City[numberOfCities];
        for (int i = 0; i < numberOfCities; i++) {
            cities[i] = new City();
        }

        Population population = new Population(TravelingSalesmanProblemUtils.POPULATION_SIZE, cities, true);
        final int initialDistance = population.getTheBestRoute()
                .getDistance();

        final TravelingSalesmanProblemSolver solver = new TravelingSalesmanProblemSolver(cities);
        population = solver.evolvePopulation(population);
        for (int i = 0; i < TravelingSalesmanProblemUtils.POPULATIONS; i++) {
            population = solver.evolvePopulation(population);

            if (i == 10 || i == 50 || i == 100 || i == 140) {
                System.out.println(i + "th path: " + population.getTheBestRoute());
                System.out.println("  Distance: " + population.getTheBestRoute()
                        .getDistance());
            }
        }

        System.out.println("\nSolution: " + population.getTheBestRoute());
        System.out.println("Initial distance: " + initialDistance);
        System.out.println("Solution distance: " + population.getTheBestRoute()
                .getDistance());
    }

    private static boolean isValidNumberOfCities(final int numberOfCities) {
        if (numberOfCities < TravelingSalesmanProblemUtils.MIN_NUMBER_OF_CITIES ||
                numberOfCities > TravelingSalesmanProblemUtils.MAX_NUMBER_OF_CITIES) {
            System.out.println("The number of the cities should be between 1 and 100!");
            return false;
        }
        return true;
    }
}
