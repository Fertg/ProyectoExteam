/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duck_hunt.graphics;

import java.awt.image.BufferedImage;
import duck_hunt.math.Vector2D;

/**
 *
 * @author elale Creación de las animaciones del juego
 */
public class Animation {

    // Contendrá los frames de la animación
    private BufferedImage[] frames;
    // Contendrá la velocidad de reproducción de la animación
    private int velocity;
    // Guarda la posición del fotograma actual que se está reproduciendo
    private int index;
    // Nos indica si la animación se está reproduciendo
    private boolean running;
    // Posición en la que se pintará la animación
    private Vector2D position;

    // Variables de control de tiempo, intentarlo con el cronometro
    private long time, lastTime;

    // Constructor de la animación
    public Animation(BufferedImage[] frames, int velocity, Vector2D position) {
        this.frames = frames;
        this.velocity = velocity;
        this.position = position;
        index = 0;
        running = true;
        time = 0;
        lastTime = System.currentTimeMillis();
    }

    // Vamos pasando el fotograma de la animación
    public void update() {
        //Controlamos el tiempo de la animacion
        time += System.currentTimeMillis() - lastTime;
        lastTime = System.currentTimeMillis();
        // Si el tiempo es mayor que la velocidad de reproducción del frame, cambiamos de frame.
        if (time > velocity) {
            // Aumentamos fotograma
            index++;
            // Reseteamos el tiempo
            time = 0;
            // Si ya no hay mas frames para reproducir
            if (index >= frames.length) {
                running = false;
            }
        }
    }

    public boolean isRunning() {
        return running;
    }

    public Vector2D getPosition() {
        return position;
    }

    // Devolvemos el frame actual de la animación
    public BufferedImage getCurrentFrame() {
        return frames[index];
    }

}
