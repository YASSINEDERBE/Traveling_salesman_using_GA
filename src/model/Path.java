package model;


import java.util.ArrayList;
import java.util.List;

public class Path {
    private List<City> cities;
    private double totalDistance;

    public Path(List<City> cities) {
        this.cities = new ArrayList<>(cities);
        this.totalDistance = calculateTotalDistance();
    }

    public List<City> getCities() {
        return cities;
    }

    public double getTotalDistance() {
        return totalDistance;
    }

    private double calculateTotalDistance() {
        double distance = 0;
        for (int i = 0; i < cities.size() - 1; i++) {
            distance += cities.get(i).distanceTo(cities.get(i + 1));
        }
        distance += cities.get(cities.size() - 1).distanceTo(cities.get(0));
        return distance;
    }

    @Override
    public String toString() {
        return "Total Distance: " + totalDistance + " Path: " + cities;
    }
}
