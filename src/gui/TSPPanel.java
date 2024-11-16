package gui;

import algorithm.MultiObjectiveGA;
import model.City;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static java.awt.Font.BOLD;
import static java.awt.Font.MONOSPACED;

public class TSPPanel extends JPanel {
    private final List<City> cities;
    private Image[] townIcons;
    private Image personIcon;
    private final int ICON_WIDTH = 90;
    private final int ICON_HEIGHT = 90;

    private final JTextField startingTownInput;
    private final JTextArea bestPathDisplay;
    private int currentCityIndex = 0;
    private Timer animationTimer;
    private double iconX, iconY;
    private double targetX;
    private double deltaX, deltaY;
    private List<MultiObjectiveGA.Individual> bestPaths = new ArrayList<>();
    private List<Line2D> trajectoryLines = new ArrayList<>();  // List to store drawn lines
    private static final int ANIMATION_DELAY = 9;
    private static final int STEP_SIZE = 50;

    public TSPPanel(List<City> cities) {
        this.cities = cities;
        setLayout(new BorderLayout());

        loadIcons();

        JPanel controlPanel = new JPanel();
        startingTownInput = new JTextField(10);
        JButton startButton = new JButton("Start Journey");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startJourney();
            }
        });

        controlPanel.add(new JLabel("Starting City:"));
        controlPanel.add(startingTownInput);
        controlPanel.add(startButton);

        bestPathDisplay = new JTextArea(1, 40);
        bestPathDisplay.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(bestPathDisplay);
        JPanel sidePanel = new JPanel(new BorderLayout());
        sidePanel.add(new JLabel("Best Path:"), BorderLayout.NORTH);
        sidePanel.add(scrollPane, BorderLayout.CENTER);

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
        Font font = new Font("Monospaced", Font.BOLD, 20);
        g2d.setColor(Color.BLACK);
        g2d.setFont(font);

        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Draw all the trajectory lines (from the previous path)

        g2d.setStroke(new BasicStroke(3));
        for (Line2D line : trajectoryLines) {
            g2d.draw(line);
        }
        // total distance
        double totalDistance =0.0;
        double totalTime =0.0;
        for(MultiObjectiveGA.Individual i : bestPaths){
            totalDistance+= i.getDistance();
            totalTime+= i.getTime();

        }

        g2d.drawString("Distance: " + totalDistance  , 550 , 70 );
        g2d.drawString("Time: " + totalTime , 950 , 70 );

        // Draw city icons
        for (int i = 0; i < cities.size(); i++) {
            City city = cities.get(i);
            int x = (int) city.getPosition().getX();
            int y = (int) city.getPosition().getY();

            Image resizedIcon = townIcons[i % townIcons.length].getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
            g2d.drawImage(resizedIcon, x - ICON_WIDTH / 2, y - ICON_HEIGHT / 2, null);
            g2d.drawString(city.getName(), x + 10, y);
        }

        // Draw person icon if journey has started
        if (!bestPaths.isEmpty() && currentCityIndex < bestPaths.get(0).getRoute().size()) {
            g2d.drawImage(personIcon.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH), (int) iconX, (int) iconY, null);
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

            iconX = startingCity.getPosition().getX() - ICON_WIDTH / 2;
            iconY = startingCity.getPosition().getY() - ICON_HEIGHT / 2;

            updateBestPathDisplay(firstPath);

            currentCityIndex = 0;
            moveToNextCity(firstPath);
        } else {
            JOptionPane.showMessageDialog(this, "No valid routes found!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateBestPathDisplay(List<City> path) {
        StringBuilder pathText = new StringBuilder();
        for (City city : path) {
            pathText.append(city.getName()).append(" -> ");
        }
        pathText.append(path.get(0).getName());
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
        if (currentCityIndex >= path.size() - 1) {
            JOptionPane.showMessageDialog(this, "Journey completed!", "Info", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        City currentCity = path.get(currentCityIndex);
        City nextCity = path.get(currentCityIndex + 1);

        double startX = currentCity.getPosition().getX() - ICON_WIDTH / 2;
        double startY = currentCity.getPosition().getY() - ICON_HEIGHT / 2;
        targetX = nextCity.getPosition().getX() - ICON_WIDTH / 2;
        double targetY = nextCity.getPosition().getY() - ICON_HEIGHT / 2;

        double distance = Math.hypot(targetX - startX, targetY - startY);
        int steps = (int) (distance / STEP_SIZE);
        deltaX = (targetX - startX) / steps;
        deltaY = (targetY - startY) / steps;

        // Add the line to the trajectoryLines list
        trajectoryLines.add(new Line2D.Double(startX +20 , startY + 50, targetX +20, targetY +50));

        animationTimer = new Timer(ANIMATION_DELAY, new ActionListener() {
            int step = 0;
            public void actionPerformed(ActionEvent e) {
                if (step >= steps) {
                    animationTimer.stop();
                    currentCityIndex++;
                    moveToNextCity(path);
                } else {
                    iconX += deltaX;
                    iconY += deltaY;
                    step++;
                    repaint();
                }
            }
        });
        animationTimer.start();
    }
}
