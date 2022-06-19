package pang.states;

import bubble_shooter.graphics.Loader;
import pang.gameObject.Constants;
import pang.graphics.Assets;
import static pang.graphics.Assets.loadFont;
import pang.graphics.Text;
import java.awt.Color;
import java.awt.Font;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import static pang.graphics.Assets.loadImage;
import pang.math.Vector2D;

/**
 *
 * @author Fernando
 */
public class LoadingState extends State {
//Hilo de carga de recursos,Es un hilo

    private Thread loadingThread;
//    Fuente del texto cargando
    private Font font;
//    private BufferedImage image;

    public LoadingState(Thread loadingThread) {
        this.loadingThread = loadingThread;
        this.loadingThread.start();
//Cogemos la fuente
        font = loadFont("/res/pang/font/kenney_blocks.ttf", 38);

    }

    @Override
    public void update() {
//        Si ha finalizado la carga de recursos
        if (Assets.loaded) {
            State.changeState(new MenuState());
            try {
                loadingThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void draw(Graphics g) {
        
        //CAMBIAMOS EL ASSET DEL MENU DE CARGA
g.drawImage(Assets.png, 0,0,null );
//  Dibujamos el rectangulo
        GradientPaint gp = new GradientPaint(
                // Le pasamos las coordenadas Ancho entre dos menos la barra entre dos
                Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2,
                Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2,
                Color.WHITE,
                Constants.WIDTH / 2 + Constants.LOADING_BAR_WIDTH / 2,
                Constants.HEIGHT / 2 + Constants.LOADING_BAR_HEIGHT / 2,
                Color.BLUE
        );

// Necesitamos el objeto Graphivs2D para pintar el gradiente
        Graphics2D g2d = (Graphics2D) g;
        
        g2d.setPaint(gp);
        
//        Obtenemos el porcentaje de carga de recursos
        float percentage = (Assets.count / Assets.MAX_COUNT);
//        Pintamos el rectangulo segun el porcentaje completado.
//         a lo alto entero completo
        g2d.fillRect(Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2,
                Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2,
                (int) (Constants.LOADING_BAR_WIDTH * percentage),
                Constants.LOADING_BAR_HEIGHT);
        g2d.drawRect(Constants.WIDTH / 2 - Constants.LOADING_BAR_WIDTH / 2,
                Constants.HEIGHT / 2 - Constants.LOADING_BAR_HEIGHT / 2,
                Constants.LOADING_BAR_WIDTH,
                Constants.LOADING_BAR_HEIGHT);
//Pintamos el texto y lo ubicamos
        Text.drawText(g2d, "PANG PANG EXTEAM", new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2 - 50),
                true, Color.WHITE, font);
//Pintamos el texto de cargar dentro del rectangulo
        Text.drawText(g2d, "CARGANDO...", new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2 + 40),
                true, Color.WHITE, font);
    }

}
