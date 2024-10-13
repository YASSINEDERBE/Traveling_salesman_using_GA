package gui;

import algorithm.GreedyTSP;
import model.City;
import model.Path;

import javax.swing.*;
import java.util.Arrays;
import java.util.List;

public class TSPFrame extends JFrame {
    public TSPFrame() {
        setTitle("Traveling Salesman Problem Visualization");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        List<City> cities = Arrays.asList(
            new City("A", 100, 200),
            new City("B", 500, 300),
            new City("C", 700, 500),
            new City("D", 900, 400),
            new City("E", 900, 200),
            new City("F", 1200, 400),
            new City("G", 1120, 900),
            new City("H", 1000, 800),
            new City("I", 350, 450),
            new City("J", 150, 700)
        );

        GreedyTSP solver = new GreedyTSP();
        Path shortestPath = solver.findShortestPath(cities);

        TSPPanel panel = new TSPPanel(cities, shortestPath);
        add(panel);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TSPFrame frame = new TSPFrame();
            frame.setVisible(true);
        });
    }
}
