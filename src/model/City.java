package model;

public class City {
    private String name;
    private Position position;

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public City(String name, Position position) {
        this.name = name;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public double distanceTo(City other) {
        return Math.sqrt(Math.pow(this.position.getX() - other.position.getX(), 2) + Math.pow(this.position.getY() - other.position.getY(), 2));
    }

    public double timeTo(City other) {
        return distanceTo(other) / 50; // Par exemple, si la vitesse est de 50 unités
    }
}
