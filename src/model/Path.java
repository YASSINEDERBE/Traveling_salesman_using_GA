package model;

import java.util.List;

public class Path {
    private List<City> cities;

    public Path(List<City> cities) {
        this.cities = cities;
    }

    public List<City> getCities() {
        return cities;
    }
}
