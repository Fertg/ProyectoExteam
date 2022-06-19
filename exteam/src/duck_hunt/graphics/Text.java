package duck_hunt.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import duck_hunt.math.Vector2D;

/**
 * Clase que define las propiedades de un objeto texto.
 * 
 * @author elale
 */
public class Text {

    // Elemento graphics donde dibujamos el texto.
    // String con el texto que queremos mostrar.
    // Posición donde vamos a dibujar el texto.
    // Boolean para centrar el texto.
    // Color del texto.
    // Fuente del texto.
    public static void drawText(Graphics g, String text, Vector2D pos, boolean center, Color color, Font font) {
        g.setColor(color);
        g.setFont(font);
        Vector2D position = new Vector2D(pos.getX(), pos.getY());
        // Si estará centrado.
        if (center) {
            // Utilizaremos FontMetrics. Lo utilizaremos para trabajar con las dimensiones del texto.
            FontMetrics fm = g.getFontMetrics();
            // Pintamos el texto centrado en base a su ancho y alto.
            position.setX(position.getX() - fm.stringWidth(text) / 2);
            position.setY(position.getY() - fm.getHeight() / 2);
        }
        // Pintamos el texto en la pantalla.
        g.drawString(text, (int) position.getX(), (int) position.getY());
    }
}
