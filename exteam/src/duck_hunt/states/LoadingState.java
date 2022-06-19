package duck_hunt.states;

import duck_hunt.gameObjects.Constants;
import duck_hunt.graphics.Assets;
import static duck_hunt.graphics.Assets.loadFont;
import duck_hunt.graphics.Text;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.logging.Level;
import java.util.logging.Logger;
import duck_hunt.math.Vector2D;

/**
 * Clase que contiene los atributos de la pantalla de carga.
 *
 * @author elale
 */
public class LoadingState extends State {

    // Hilo de carga de recursos.
    private Thread loadingThread;
    // Fuente del texto cargando, para que cargue independiente al hilo de los assets.
    public static Font font;
    // Constructor.
    public LoadingState(Thread loadingThread) {
        this.loadingThread = loadingThread;
        this.loadingThread.start();
        font = loadFont("/res/duck_hunt/fonts/kenney_blocks.ttf", 38);

    }

    // Actualizar.
    @Override
    public void update() {
        // Si ha finalizado la carga de recursos.
        if (Assets.loader) {
            // Cargamos el menu.
            State.changeState(new MenuState());
            try {
                // Finalizamos el hilo de carga de recursos.
                loadingThread.join();
            } catch (InterruptedException ex) {
                Logger.getLogger(LoadingState.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    // Pintamos la barra de carga.
    @Override
    public void draw(Graphics g) {
        g.drawImage(Assets.duck, 0,0,null );
        // Dibujamos el objeto rectangulo.
        GradientPaint gp = new GradientPaint(
                Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2, // Mitad del ancho menos mitad de la barra.
                Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2, // Mitad del alto menos mitad de la barra.
                Color.WHITE, // Color blanco.
                Constants.WIDTH / 2 + Constants.LOADING_BAR_WIDTH / 2, // Mitad del ancho mas mitad de la barra.
                Constants.WIDTH / 2 + Constants.LOADING_BAR_HEIGHT / 2, // Mitad del alto mas la mitad de la barra.
                Color.BLUE); // Color azul.
        // Neccesitamos el objeto g2d para pintar el gradiente.
        Graphics2D g2d = (Graphics2D) g;
        g2d.setPaint(gp);
        // Obtenemos el porcentaje de carga de recursos.
        float percentage = (Assets.count / Assets.MAX_COUNT);
        // Rellenamos el rectangulo según el porcentaje completado.
        g2d.fillRect(
                Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2, // Mitad del ancho menos mitad de la barra
                Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2, // Mitad del alto menos mitad de la barra.
                (int) ((Constants.LOADING_BAR_WIDTH * percentage)), // Barra * porcentaje de carga (0 a 1).
                Constants.LOADING_BAR_HEIGHT // Alto de la barra.
        );
        // Dibujamos un marco alrededor del rectángulo. Le damos un par de píxeles de margen entre la barra de carga y el marco.
        g2d.drawRect(
                Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2 - 2, // 2 pixeles.
                Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2 - 2, // 2 pixeles.
                Constants.LOADING_BAR_WIDTH + 4, // 4 pixeles, porque tenemos que sumar el desplazamiento horizontal de los 2 píxeles de arriba.
                Constants.LOADING_BAR_HEIGHT + 4); // 4 pixeles, porque tenemos que sumar el desplazamiento vertical de los 2 píxeles de arriba.
        // Pintamos los textos de nuestra clase cargando.
        Text.drawText(g2d, "DUCK HUNT", new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2 - 50), true, Color.WHITE, font);
        Text.drawText(g2d, "CARGANDO...", new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2 + 40), true, Color.WHITE, font);
    }

}
