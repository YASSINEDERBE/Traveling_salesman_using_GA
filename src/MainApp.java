

import javax.swing.*;

import ExtensionMethods.RenderPanel;

public class MainApp extends JFrame {
    public MainApp() {
        setTitle("Java Swing Example");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        RenderPanel renderPanel = new RenderPanel();
        add(renderPanel);
    }

    public static void main (String[] args) {
        SwingUtilities.invokeLater(() -> {
            MainApp frame = new MainApp();
            frame.setVisible(true);
        });
    }
}
