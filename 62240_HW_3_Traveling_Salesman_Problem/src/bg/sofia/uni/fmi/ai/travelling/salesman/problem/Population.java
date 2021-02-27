package bg.sofia.uni.fmi.ai.travelling.salesman.problem;

public class Population {
    private final Route[] routes;

    public Population(final int populationSize) {
        this.routes = new Route[populationSize];
    }

    public Population(final int populationSize, final City[] cities, final boolean isFirstPopulation) {
        this(populationSize);
        if (isFirstPopulation) {
            for (int i = 0; i < populationSize; i++) {
                final Route route = new Route(cities.length);
                route.generateIndividual(cities);
                this.routes[i] = route;
            }
        }
    }

    public void saveRoute(final int index, final Route route) {
        this.routes[index] = route;
    }

    public Route getRoute(final int index) {
        return this.routes[index];
    }

    public Route getTheBestRoute() {
        Route route = routes[0];
        for (int i = 1; i < routes.length; i++) {
            if (route.getFitness() <= routes[i].getFitness()) {
                route = routes[i];
            }
        }
        return route;
    }

    public int populationSize() {
        return routes.length;
    }
}
