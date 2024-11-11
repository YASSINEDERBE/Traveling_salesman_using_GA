package model;

public class Position {
    private double x; // Coordonnée X
    private double y; // Coordonnée Y

    public Position(double x, double y) {
        this.y = y;
        this.x = x;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }
}
