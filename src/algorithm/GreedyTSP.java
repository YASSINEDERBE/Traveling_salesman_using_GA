package algorithm;


import model.City;
import model.Path;

import java.util.ArrayList;
import java.util.List;

public class GreedyTSP {

    public Path findShortestPath(List<City> cities) {
        List<City> visitedCities = new ArrayList<>();
        City currentCity = cities.get(0); 
        visitedCities.add(currentCity);

        while (visitedCities.size() < cities.size()) {
            City nearestCity = findNearestNeighbor(currentCity, cities, visitedCities);
            visitedCities.add(nearestCity);
            currentCity = nearestCity;
        }

        return new Path(visitedCities);
    }

    private City findNearestNeighbor(City currentCity, List<City> cities, List<City> visitedCities) {
        City nearestCity = null;
        double shortestDistance = Double.MAX_VALUE;

        for (City city : cities) {
            if (!visitedCities.contains(city)) {
                double distance = currentCity.distanceTo(city);
                if (distance < shortestDistance) {
                    shortestDistance = distance;
                    nearestCity = city;
                }
            }
        }
        return nearestCity;
    }
}
