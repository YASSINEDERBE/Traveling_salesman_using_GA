package gui;

import model.City;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TSPFrame extends JFrame {
    public TSPFrame(List<City> cities) {
        setTitle("Traveling Salesman Problem");
        setSize(800, 600); 
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); 

        TSPPanel tspPanel = new TSPPanel(cities);
        add(tspPanel); 
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // Créer quelques villes pour l'exemple
            List<City> cities = List.of(
                new City("City 1", 100, 100),
                new City("City 2", 200, 150),
                new City("City 3", 300, 200),
                new City("City 4", 400, 250),
                new City("City 5", 500, 300),
                new City("City 6", 600, 350),
                new City("City 7", 700, 400),
                new City("City 8", 150, 450),
                new City("City 9", 250, 500),
                new City("City 10", 350, 550)
            );

            TSPFrame frame = new TSPFrame(cities);
            frame.setVisible(true); // Afficher la fenêtre
        });
    }
}
