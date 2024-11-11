package main;

import gui.TSPPanel;
import model.City;
import model.Position;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main {
    public static void main(String[] args) {
//        List<City> cities = new ArrayList<>();
        Random random = new Random();
        int numCities = 10;



        List<City> cities = List.of(
                new City("City 1", new Position(250 ,130)),
                new City("City 2", new Position(550 ,450)),
                new City("City 3",new Position(1200,750)),
                new City("City 4", new Position(690 ,189)),
                new City("City 5", new Position(1200 ,183)),
                new City("City 6", new Position(950 ,400)),
                new City("City 7", new Position(175 ,300)),
                new City("City 8", new Position(336 ,770)),
                new City("City 9", new Position(700, 650))
        );

//        for (int i = 1; i <= numCities; i++) {
////            int x = random.nextInt(800) + 50;
////            int y = random.nextInt(600) + 50;
//            Position position = new Position(x, y);
//            cities.add(new City("City " + i, position));
//        }

        JFrame frame = new JFrame("TSP Simulation");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(900, 700);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setUndecorated(true);
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);

        TSPPanel tspPanel = new TSPPanel(cities);
        frame.add(tspPanel);

        frame.setVisible(true);
    }
}
