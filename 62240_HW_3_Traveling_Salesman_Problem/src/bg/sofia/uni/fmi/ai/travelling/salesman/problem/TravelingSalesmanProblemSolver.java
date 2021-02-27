package bg.sofia.uni.fmi.ai.travelling.salesman.problem;

public class TravelingSalesmanProblemSolver {
    private final City[] cities;

    public TravelingSalesmanProblemSolver(final City[] cities) {
        this.cities = cities;
    }

    public Population evolvePopulation(final Population population) {
        final Population newPopulation = new Population(population.populationSize(), cities, false);
        newPopulation.saveRoute(0, population.getTheBestRoute());

        for (int i = TravelingSalesmanProblemUtils.ELITISM; i < population.populationSize(); i++) {
            final Route firstParent = tournamentSelection(population);
            final Route secondParent = tournamentSelection(population);
            final Route child = crossover(firstParent, secondParent);

            newPopulation.saveRoute(i, child);
        }

        for (int i = TravelingSalesmanProblemUtils.ELITISM; i < population.populationSize(); i++) {
            mutate(newPopulation.getRoute(i));
        }

        return newPopulation;
    }

    /**
     * Two-point crossover
     */
    public Route crossover(Route firstParent, Route secondParent) {
        final Route child = new Route(cities.length);
        int firstPosition = (int) (Math.random() * firstParent.getRouteSize());
        int secondPosition = (int) (Math.random() * firstParent.getRouteSize());

        final int startPosition = Math.min(firstPosition, secondPosition);
        final int endPosition = Math.max(firstPosition, secondPosition);

        for (int i = startPosition; i < endPosition; i++) {
            child.setCity(i, firstParent.getCity(i));
        }

        int index = endPosition;
        for (int i = endPosition; i < secondParent.getRouteSize(); i++) {
            if (!child.containsCity(secondParent.getCity(i))) {
                if (index >= child.getRouteSize()) {
                    index = 0;
                }
                child.setCity(index, secondParent.getCity(i));
                index++;
            }
        }

        for (int i = 0; i < endPosition; i++) {
            if (!child.containsCity(secondParent.getCity(i))) {
                if (index >= child.getRouteSize()) {
                    index = 0;
                }
                child.setCity(index, secondParent.getCity(i));
                index++;
            }
        }

        return child;
    }

    private void mutate(final Route route) {
        for (int i = 0; i < route.getRouteSize(); i++) {
            if (Math.random() < TravelingSalesmanProblemUtils.MUTATION_RATE) {
                int newIndex = (int) (route.getRouteSize() * Math.random());

                final City firstCity = route.getCity(i);
                final City secondCity = route.getCity(newIndex);

                route.setCity(newIndex, firstCity);
                route.setCity(i, secondCity);
            }
        }
    }

    private Route tournamentSelection(Population population) {
        final Population tournament = new Population(TravelingSalesmanProblemUtils.TOURNAMENT_SIZE, cities, false);
        for (int i = 0; i < TravelingSalesmanProblemUtils.TOURNAMENT_SIZE; i++) {
            int randomId = (int) (Math.random() * population.populationSize());
            tournament.saveRoute(i, population.getRoute(randomId));
        }

        return tournament.getTheBestRoute();
    }
}
