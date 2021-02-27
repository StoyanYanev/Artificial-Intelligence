package bg.sofia.uni.fmi.ai.travelling.salesman.problem;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Route {
    private City[] cities;
    private double fitness;
    private int distance;

    public Route(final int numberOfCities) {
        this.cities = new City[numberOfCities];
        this.fitness = 0;
        this.distance = 0;
    }

    public int getDistance() {
        if (distance == 0) {
            int pathDistance = 0;
            for (int i = 0; i < cities.length; i++) {
                final City fromCity = cities[i];
                if (i + 1 < cities.length) {
                    final City toCity = cities[i + 1];
                    pathDistance += fromCity.distanceTo(toCity);
                }
            }
            distance = pathDistance;
        }
        return distance;
    }

    public double getFitness() {
        if (fitness == 0) {
            fitness = 1 / (double) getDistance();
        }
        return fitness;
    }

    public City getCity(final int index) {
        return cities[index];
    }

    public void setCity(final int index, final City city) {
        this.cities[index] = city;
    }

    public void generateIndividual(final City[] cities) {
        List<City> c = Arrays.asList(cities);
        Collections.shuffle(c);
        this.cities = c.toArray(new City[0]);
    }

    public boolean containsCity(final City city) {
        return Arrays.stream(cities)
                .sequential()
                .anyMatch(c -> c != null && c.equals(city));
    }

    public int getRouteSize() {
        return cities.length;
    }

    @Override
    public String toString() {
        final StringBuilder routeString = new StringBuilder();
        for (int i = 0; i < cities.length; i++) {
            routeString.append(getCity(i))
                    .append(" ");
        }
        return routeString.toString();
    }
}
