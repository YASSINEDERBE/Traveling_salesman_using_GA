package model;

public class City {
    private String name;
    private double x; // Coordonnée X
    private double y; // Coordonnée Y

    public City(String name, double x, double y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public String getName() {
        return name;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double distanceTo(City other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    public double timeTo(City other) {
        return distanceTo(other) / 50; // Par exemple, si la vitesse est de 50 unités
    }
}
