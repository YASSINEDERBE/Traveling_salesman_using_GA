package gui;



import model.City;
import model.Path;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;

public class TSPPanel extends JPanel {
    private List<City> cities;
    private Path path;
    private Image[] townIcons; 
    private Image personIcon; 
    private final int ICON_WIDTH = 150; 
    private final int ICON_HEIGHT = 150; 
    
    private JTextField startingTownInput; 
    private JButton startButton; 
    private int currentCityIndex = -1; 
    private Timer animationTimer; 
    private int iconX, iconY; 

    public TSPPanel(List<City> cities, Path path) {
        this.cities = cities;
        this.path = path;
        loadIcons();
        
        startingTownInput = new JTextField(10); 
        startButton = new JButton("Start Journey");
        
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                startJourney();
            }
        });

        add(startingTownInput);
        add(startButton);
    }

    private void loadIcons() {
        townIcons = new Image[10];
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
            int x = (int) city.getX();
            int y = (int) city.getY();

            Image resizedIcon = townIcons[i % townIcons.length].getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH);
            g2d.drawImage(resizedIcon, x - ICON_WIDTH / 2, y - ICON_HEIGHT / 2, null); // Center the icon
            g2d.drawString(city.getName(), x + 10, y);
        }

        if (currentCityIndex != -1) {
            g2d.drawImage(personIcon.getScaledInstance(ICON_WIDTH, ICON_HEIGHT, Image.SCALE_SMOOTH), iconX, iconY, null);
        }
    }

    private void startJourney() {
        String startingTownName = startingTownInput.getText().trim();
        currentCityIndex = findCityIndexByName(startingTownName);
        
        if (currentCityIndex == -1) {
            JOptionPane.showMessageDialog(this, "City not found!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        City startCity = cities.get(currentCityIndex);
        iconX = (int) startCity.getX() - ICON_WIDTH / 2;
        iconY = (int) startCity.getY() - ICON_HEIGHT / 2;

        if (animationTimer != null && animationTimer.isRunning()) {
            animationTimer.stop();
        }
        
        animationTimer = new Timer(1000, new ActionListener() { 
            public void actionPerformed(ActionEvent e) {
                moveToNextCity();
            }
        });
        animationTimer.start();
    }

    private int findCityIndexByName(String name) {
        for (int i = 0; i < cities.size(); i++) {
            if (cities.get(i).getName().equalsIgnoreCase(name)) {
                return i;
            }
        }
        return -1; 
    }

    private void moveToNextCity() {
        if (path == null) {
            animationTimer.stop();
            return; 
        }

        
        currentCityIndex++;
        
        if (currentCityIndex < path.getCities().size()) {
            City nextCity = path.getCities().get(currentCityIndex);
            iconX = (int) nextCity.getX() - ICON_WIDTH / 2;
            iconY = (int) nextCity.getY() - ICON_HEIGHT / 2;
        } else {
            JOptionPane.showMessageDialog(this, "Journey completed!", "Info", JOptionPane.INFORMATION_MESSAGE);
            animationTimer.stop();
            return; 
        }

        repaint();
    }
}

