package gui;

import algorithm.MultiObjectiveGA;
import model.City;
import model.Position;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TSPPanel extends JPanel {
    private List<City> cities;
    private Image[] townIcons;
    private Image personIcon;
    private final int ICON_WIDTH = 60;
    private final int ICON_HEIGHT = 60;

    private JTextField startingTownInput;
    private JButton startButton;
    private JTextArea bestPathDisplay;  // Panneau pour afficher le meilleur chemin
    private int currentCityIndex = 0;
    private Timer animationTimer;
    private int iconX, iconY;
    private List<MultiObjectiveGA.Individual> bestPaths = new ArrayList<>(); // Initialiser ici

    public TSPPanel(List<City> cities) {
        this.cities = cities;
        setLayout(new BorderLayout());
        
        loadIcons();

        // Panneau de contrôle en haut
        JPanel controlPanel = new JPanel();
        startingTownInput = new JTextField(10);
        startButton = new JButton("Start Journey");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startJourney();
            }
        });

        controlPanel.add(new JLabel("Starting City:"));
        controlPanel.add(startingTownInput);
        controlPanel.add(startButton);
        
        // Panneau pour l'affichage du meilleur chemin
        bestPathDisplay = new JTextArea(120, 20);
        bestPathDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(bestPathDisplay);
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.add(new JLabel("Best Path:"), BorderLayout.NORTH);
        sidePanel.add(scrollPane, BorderLayout.CENTER);

        // Ajouter les panneaux au TSPPanel
        add(controlPanel, BorderLayout.NORTH);
        add(sidePanel, BorderLayout.EAST);
    }

    private void loadIcons() {
        townIcons = new Image[cities.size()];
        for (int i = 0; i < townIcons.length; i++) {
            try {
                townIcons[i] = ImageIO.read(getClass().getResource("/Resources/Town_" + (i + 1) + ".png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            personIcon = ImageIO.read(getClass().getResource("/Resources/person.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        for (int i = 0; i < cities.size(); i++) {
            City city = cities.get(i);
            int x = (int) city.getPosition().getX();
            int y = (int) city.getPosition().getY();

            Image resizedIcon = townIcons[i % townIcons.length].getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
            g2d.drawImage(resizedIcon, x - ICON_WIDTH / 2, y - ICON_HEIGHT / 2, null);
            g2d.drawString(city.getName(), x + 10, y);
        }

        if (!bestPaths.isEmpty() && currentCityIndex < bestPaths.size()) {
            g2d.drawImage(personIcon.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH), iconX, iconY, null);
        }
    }
    private void startJourney() {
        String startingTownName = startingTownInput.getText().trim();
        int startCityIndex = findCityIndexByName(startingTownName);

        if (startCityIndex == -1) {
            JOptionPane.showMessageDialog(this, "Please check the city name", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        MultiObjectiveGA ga = new MultiObjectiveGA(cities, 100, 1000, 0.01);
        bestPaths = ga.run();

        if (!bestPaths.isEmpty()) {
            List<City> firstPath = new ArrayList<>(bestPaths.get(0).getRoute());

            City startingCity = cities.get(startCityIndex);
            while (!firstPath.get(0).equals(startingCity)) {
                firstPath.add(firstPath.remove(0));
            }

            if (!firstPath.get(firstPath.size() - 1).equals(startingCity)) {
                firstPath.add(startingCity);
            }

            iconX = (int) startingCity.getPosition().getX() - ICON_WIDTH / 2;
            iconY = (int) startingCity.getPosition().getX() - ICON_HEIGHT / 2;

            // Mettre à jour l'affichage du meilleur chemin
            updateBestPathDisplay(firstPath);

            currentCityIndex = 0; 
            animationTimer = new Timer(1000, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    moveToNextCity(firstPath);
                }
            });
            animationTimer.start();
        } else {
            JOptionPane.showMessageDialog(this, "No valid routes found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBestPathDisplay(List<City> path) {
        StringBuilder pathText = new StringBuilder();
        for (City city : path) {
            pathText.append(city.getName()).append(" -> ");
        }
        pathText.append(path.get(0).getName()); // Boucle pour revenir à la ville de départ
        bestPathDisplay.setText(pathText.toString());
    }

    private int findCityIndexByName(String name) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1;
    }

    private void moveToNextCity(List<City> path) {
        if (currentCityIndex >= path.size()) {
            animationTimer.stop();
            JOptionPane.showMessageDialog(this, "Journey completed!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        City currentCity = path.get(currentCityIndex);
        iconX = (int) currentCity.getPosition().getX() - ICON_WIDTH / 2;
        iconY = (int) currentCity.getPosition().getY() - ICON_HEIGHT / 2;

        currentCityIndex++;
        repaint();
    }
}
