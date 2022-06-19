package pang.graphics;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import pang.math.Vector2D;

/**
 *
 * @author Fernando
 */

//Clase de texto
public class Text {
//   Recibe graficos cadena Texto,  vector posicion center =  nos indica donde pintar	

    public static void drawText(Graphics g, String text, Vector2D pos, boolean center, Color color, Font font) {
//Añadimos al grafico color y la fuente
        g.setColor(color);
        g.setFont(font);
// Creamos el vector posicion                 
        Vector2D position = new Vector2D(pos.getX(), pos.getY());

        if (center) {
// Saber el ancho y el alto del texto y pintarlo exactamente donde queremos
            FontMetrics fm = g.getFontMetrics();
//  Pintamos el texto centrado en base a su ancho yalto
            position.setX(position.getX() - fm.stringWidth(text) / 2);
            position.setY(position.getY() - fm.getHeight() / 2);

        }
// Pintamos el texto en la pantalla le pasamos el texto y las posiciones
        g.drawString(text, (int) position.getX(), (int) position.getY());

    }
}
