package duck_hunt.input;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Clase que define las propiedades de un objeto MouseInput. Hereda de MouseAdapter.
 *
 * @author elale
 */
public class MouseInput extends MouseAdapter {

    // Coordenadas del cursor del ratón.
    public static int x, y;
    // Nos indica si el botón presionado es el izquierdo.
    public static boolean MLB;

    // Presionamos uno de los botones del ratón.
    @Override
    public void mousePressed(MouseEvent me) {
        // Comprobamos si el botón presionado es el izquierdo MLB.
        if (me.getButton() == MouseEvent.BUTTON1) {
            MLB = true;
        }
    }

    // Soltamos uno de los botones del ratón.
    @Override
    public void mouseReleased(MouseEvent me) {
        // Comprobamos si el botón soltado es el izquierdo MLB.
        if (me.getButton() == MouseEvent.BUTTON1) {
            MLB = false;
        }
    }

    // Arrastramos el cursor.
    @Override
    public void mouseDragged(MouseEvent me) {
        // Obtenemos la posición del raton.
        x = me.getX();
        y = me.getY();
    }

    // Movemos el cursor.
    @Override
    public void mouseMoved(MouseEvent me) {
        // Obtenemos la posición del raton.
        x = me.getX();
        y = me.getY();
    }
}
