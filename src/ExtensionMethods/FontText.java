package ExtensionMethods;

import java.awt.Color;
import java.awt.Font;

public class FontText {
    private String stringText;
    private Font font;
    private float scale;
    private Color textColour;

    public FontText(String stringText, Font font, float scale, Color textColour) {
        this.stringText = stringText;
        this.font = font;
        this.scale = scale;
        this.textColour = textColour;
    }

    public String getStringText() {
        return stringText;
    }

    public Font getFont() {
        return font.deriveFont(font.getSize() * scale); 
    }

    public Color getTextColour() {
        return textColour;
    }
}
