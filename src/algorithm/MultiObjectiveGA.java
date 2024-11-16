package algorithm;

import model.City;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class MultiObjectiveGA {
    private List<City> cities;
    private int populationSize;
    private int generations;
    private double mutationRate;

    public MultiObjectiveGA(List<City> cities, int populationSize, int generations, double mutationRate) {
        this.cities = cities;
        this.populationSize = populationSize;
        this.generations = generations;
        this.mutationRate = mutationRate;
    }

    public static class Individual {
        private List<City> route;
        private double distance;
        private double time;

        public Individual(List<City> route) {
            this.route = new ArrayList<>(route);
            calculateObjectives();
        }

        private void calculateObjectives() {
            distance = 0.0;
            time = 0.0;
            for (int i = 0; i < route.size() - 1; i++) {
                City from = route.get(i);
                City to = route.get(i + 1);
                distance += from.distanceTo(to);
                time += from.timeTo(to);
            }
        }

        public double getDistance() {
            return distance;
        }

        public double getTime() {
            return time;
        }

        public List<City> getRoute() {
            return route;
        }
    }

    public List<Individual> initializePopulation() {
        List<Individual> population = new ArrayList<>();
        for (int i = 0; i < populationSize; i++) {
            List<City> shuffledCities = new ArrayList<>(cities);
            Collections.shuffle(shuffledCities);
            population.add(new Individual(shuffledCities));
        }
        return population;
    }

    public List<Individual> paretoSelection(List<Individual> population) {
        List<Individual> paretoFront = new ArrayList<>();
        for (Individual ind : population) {
            boolean dominated = false;
            for (Individual other : population) {
                if (dominates(other, ind)) {
                    dominated = true;
                    break;
                }
            }
            if (!dominated) paretoFront.add(ind);
        }
        return paretoFront;
    }

    private boolean dominates(Individual a, Individual b) {
        return (a.getDistance() <= b.getDistance() && a.getTime() <= b.getTime())
                && (a.getDistance() < b.getDistance() || a.getTime() < b.getTime());
    }

    public Individual crossover(Individual parent1, Individual parent2) {
        List<City> childRoute = new ArrayList<>(parent1.getRoute().subList(0, cities.size() / 2));
        for (City city : parent2.getRoute()) {
            if (!childRoute.contains(city)) childRoute.add(city);
        }
        return new Individual(childRoute);
    }

    public void mutate(Individual individual) {
        Random rand = new Random();
        if (rand.nextDouble() < mutationRate) {
            int i = rand.nextInt(cities.size());
            int j = rand.nextInt(cities.size());
            Collections.swap(individual.getRoute(), i, j);
            individual.calculateObjectives();
        }
    }

    public List<Individual> run() {
        List<Individual> population = initializePopulation();
        for (int gen = 0; gen < generations; gen++) {
            List<Individual> newPopulation = new ArrayList<>();
            List<Individual> paretoFront = paretoSelection(population);

            while (newPopulation.size() < populationSize) {
                Individual parent1 = paretoFront.get(new Random().nextInt(paretoFront.size()));
                Individual parent2 = paretoFront.get(new Random().nextInt(paretoFront.size()));
                Individual child = crossover(parent1, parent2);
                mutate(child);
                newPopulation.add(child);
            }
            population = newPopulation;
        }
        return paretoSelection(population);
    }
}

