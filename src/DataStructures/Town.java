package DataStructures;

import Extentions.Vector2d;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class Town {
	
    private Vector2d position;
    private BufferedImage texture;
    private Rectangle2D.Float shape;
    private int id;

    public Town(Vector2d position, BufferedImage texture, int id) {
        this.position = position;
        this.texture = texture;
        this.shape = new Rectangle2D.Float(position.getX() - 150, position.getY() - 150, 300, 300); // Set origin to the center
        this.id = id;
    }

    // Draw method to render the town on a JPanel
    public void draw(Graphics2D g) {
        if (texture != null) {
            // Draw the image at the town's position
            g.drawImage(texture, (int) position.getX() - 150, (int) position.getY() - 150, 300, 300, null);
        } else {
            // Draw a fallback rectangle if no texture is available
            g.setColor(Color.RED);
            g.fill(shape);
        }
    }

    // Getter for ID
    public int getId() {
        return id;
    }
}





