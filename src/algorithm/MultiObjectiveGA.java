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

    public class Individual {
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

/*
1. Initialisation de la population

Dans la méthode initializePopulation :

    L’algorithme commence par créer une population initiale d'individus. Chaque individu représente un trajet (ou itinéraire) entre les villes dans un ordre spécifique.
    Les individus sont créés en prenant les villes dans une liste et en les mélangeant aléatoirement (Collections.shuffle). Cela permet de générer une population de trajets uniques au départ.
    Chaque itinéraire est représenté par une instance de la classe Individual, qui calcule automatiquement les valeurs de distance et de temps pour cet itinéraire dans calculateObjectives.

2. Calcul des objectifs

Dans la méthode calculateObjectives de la classe Individual :

    Pour chaque itinéraire, l'algorithme calcule la distance totale et le temps total en parcourant l’itinéraire ville par ville.
    distanceTo et timeTo (méthodes de la classe City) sont utilisées pour déterminer la distance et le temps entre deux villes consécutives.

3. Sélection du front de Pareto

Dans la méthode paretoSelection :

    L'algorithme identifie un front de Pareto dans la population.
        Un front de Pareto est un ensemble d'individus qui ne sont dominés par aucun autre dans les deux objectifs (distance et temps).
        Un individu domine un autre si ses valeurs de distance et de temps sont à la fois inférieures ou égales à celles de l'autre, et strictement inférieures dans au moins un des deux objectifs.
    La méthode paretoSelection parcourt chaque individu et utilise dominates pour vérifier s’il est dominé par un autre.
    Tous les individus non dominés forment le front de Pareto, qui est conservé pour la génération suivante.

4. Croisement (crossover)

Dans la méthode crossover :

    L'algorithme combine les trajets de deux parents pour créer un nouvel itinéraire.
    Pour cela, il copie la première moitié du trajet du premier parent dans le trajet de l’enfant.
    Ensuite, il ajoute les villes manquantes à partir du second parent, sans duplication, en garantissant que chaque ville n'apparaît qu'une fois dans le trajet de l’enfant.

5. Mutation

Dans la méthode mutate :

    L'algorithme applique une mutation aux individus avec une certaine probabilité (taux de mutation donné).
    Si la mutation a lieu, l'algorithme choisit deux villes au hasard dans le trajet et échange leur position (swap).
    Cela aide à explorer de nouvelles combinaisons dans l'itinéraire et empêche la convergence prématurée de la solution vers un optimum local.

6. Boucle Génétique

Dans la méthode run :

    L'algorithme exécute un certain nombre de générations (generations).
    Pour chaque génération :
        Il sélectionne le front de Pareto actuel (les meilleurs itinéraires trouvés).
        Il génère de nouveaux enfants à partir du front de Pareto en appliquant des croisements et des mutations.
        La nouvelle population est composée de ces enfants, et l'algorithme continue ainsi à chaque génération.
    À la fin, la méthode renvoie le dernier front de Pareto comme les meilleurs itinéraires trouvés. Cela inclut les solutions optimales qui équilibrent distance et temps.*/
