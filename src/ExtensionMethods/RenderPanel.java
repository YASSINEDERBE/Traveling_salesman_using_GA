package ExtensionMethods;

import javax.swing.*;
import java.awt.*;

public class RenderPanel extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); 
        Graphics2D g2d = (Graphics2D) g;

        draw(g2d, 50, 50, 100, 50, 1.0f); 

        FontText fontText = new FontText("Traveling salesman", new Font("Arial", Font.PLAIN, 24), 1.0f, Color.RED);
        drawString(g2d, fontText, 200, 200); 
    }

    public void draw(Graphics2D g2d, int x, int y, int width, int height, float scale) {
        g2d.setColor(Color.BLUE);
        g2d.fillRect(x, y, (int) (width * scale), (int) (height * scale));
    }

   
    public void drawString(Graphics2D g2d, FontText fontText, int x, int y) {
        g2d.setFont(fontText.getFont());
        g2d.setColor(fontText.getTextColour());

        FontMetrics metrics = g2d.getFontMetrics(fontText.getFont());
        int textWidth = metrics.stringWidth(fontText.getStringText());
        int textHeight = metrics.getHeight();

        int centerX = x - textWidth / 2;
        int centerY = y + textHeight / 4; 

        g2d.drawString(fontText.getStringText(), centerX, centerY);
    }
}
