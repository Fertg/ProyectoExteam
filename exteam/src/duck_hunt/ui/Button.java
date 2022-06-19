package duck_hunt.ui;

import duck_hunt.graphics.Assets;
import duck_hunt.graphics.Text;
import duck_hunt.input.MouseInput;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import duck_hunt.math.Vector2D;

/**
 * Clase que define las propiedades de los botones.
 *
 * @author elale
 */
public class Button {

    // Imagen cuando !hover.
    private BufferedImage mouseOutImg;
    // Imagen cuando hover.
    private BufferedImage mouseInImg;
    // Cuando está el ratón hover.
    private boolean mouseIn;

    // Objeto para detectar si el ratón está encima del botón.
    // Detectamos colisión con el mismo.
    private Rectangle boundingBox;
    // String con el texto del botón.
    private String text;
    // Objeto para gestionar la acción de cada botón
    private Action action;

    // Constructor
    public Button(BufferedImage mouseOutImg, BufferedImage mouseInImg, String text, int x, int y, Action action) {
        this.mouseOutImg = mouseOutImg;
        this.mouseInImg = mouseInImg;
        this.mouseIn = mouseIn;
        this.boundingBox = new Rectangle(x, y, mouseInImg.getWidth(), mouseInImg.getHeight());
        this.text = text;
        this.action = action;
    }

    // Update.
    public void update() {
        // Comprobamos si las coordenadas del ratón están dentro del botón.
        if (boundingBox.contains(MouseInput.x, MouseInput.y)) {
            mouseIn = true;
        } else {
            mouseIn = false;
        }
        // Si el ratón está dentro del botón y además hacemos click.
        if (mouseIn && MouseInput.MLB) {
            // Ejecutamos la acción vinculada al botón.
            action.doAction();
        }
    }

    // Dibujamos el botón.
    public void draw(Graphics g) {
        // Si el ratón está hover, pinta imagen In, si no, imagen Out.
        if (mouseIn) {
            g.drawImage(mouseInImg, boundingBox.x, boundingBox.y, null);
        } else {
            g.drawImage(mouseOutImg, boundingBox.x, boundingBox.y, null);
        }
        // Pintamos el texto del botón.
        Text.drawText(g,
                text,
                new Vector2D(
                        boundingBox.getX() + boundingBox.getWidth() / 2,
                        boundingBox.getY() + boundingBox.getHeight()),
                true,
                Color.BLACK,
                Assets.fontMed);
    }

}
