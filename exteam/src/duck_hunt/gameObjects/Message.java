package duck_hunt.gameObjects;

import duck_hunt.graphics.Text;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import duck_hunt.math.Vector2D;
import duck_hunt.states.GameState;

/**
 * Clase que define los atributos de un objeto Message. Estos serán los mensajes que veremos mientras estamos jugando.
 *
 * @author elale
 */
public class Message {

    // Posición del texto a pintar
    private Vector2D position;
    // Efecto de aparecer
    private boolean fade;
    // Cadena de texto
    private String text;
    // Color del texto
    private Color color;
    // Indica si queremos que lo pinte centrado
    private boolean center;
    // Fuente del texto
    private Font font;
    // Instancia de GameState;
    private GameState gameState;
    // Valor que indica la transparencia: 1 se ve, 0 no se ve.
    private float alpha;
    // Variable delta que nos indicará cuanto tiempo tardará el texto en aparecer o desaparecer.
    // Alpha tomará valores entre 0 y 1, sumándo en cada actualización el valor DELTA_ALPHA.
    private final float DELTA_ALPHA = 0.01f;

    // Constructor de Message.
    public Message(Vector2D position, boolean fade, String text, Color color, boolean center, Font font, GameState gameState) {
        this.position = position;
        this.fade = fade;
        this.text = text;
        this.color = color;
        this.center = center;
        this.font = font;
        this.gameState = gameState;
        if (fade) {
            alpha = 1;
        } else {
            alpha = 0;
        }
    }

    // Método draw.
    public void draw(Graphics2D g2d) {

        // Aplicamos la transparencia al objeto gráfico.
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        // Pintamos el texto.
        Text.drawText(g2d, text, position, center, color, font);
        // Actualizamos la posición del texto.
        position.setY(position.getY() - 1);
        // Aplicamos la opacidad al objeto gráfico.
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));

        // Actualizar la variable alpha en función de si queremos que el texto aparezca o desaparezca.
        if (fade) {
            alpha -= DELTA_ALPHA;
        } else {
            alpha += DELTA_ALPHA;
        }

        // Cuando alpha sea menos que cero lo eliminamos.
        if (fade && alpha < 0) {
            gameState.getMessages().remove(this);
        }
        // Cuando alpha sea mayor que 1, se igualará a 1 y se activará la bandera fade.
        if (!fade && alpha > 1) {
            fade = true;
            alpha = 1;
        }
    }

}
