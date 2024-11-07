package main;

import gui.TSPPanel;
import model.City;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
        List<City> cities = new ArrayList<>();
        Random random = new Random();
        int numCities = 10;

        for (int i = 1; i <= numCities; i++) {
            int x = random.nextInt(800) + 50;  
            int y = random.nextInt(600) + 50;
            cities.add(new City("City " + i, x, y));
        }

        JFrame frame = new JFrame("TSP Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(900, 700);
        frame.setLocationRelativeTo(null);

        TSPPanel tspPanel = new TSPPanel(cities);
        frame.add(tspPanel);

        frame.setVisible(true);
    }
}
