package duck_hunt.gameObjects;

import duck_hunt.graphics.Assets;
import duck_hunt.graphics.Sound;
import duck_hunt.input.KeyBoard;
import duck_hunt.input.MouseInput;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import duck_hunt.math.Vector2D;
import duck_hunt.states.GameState;

/**
 *
 *
 * @author elale
 */
public class Crosshair extends MovingObject {

    // Creamos un chrono
    private Chronometer firerate;
    // Sonido disparo
    private Sound shootSound;
    // Bandera de disparo
    public boolean trigger;
    //pause
    public boolean pauseG = false;

    public boolean isTrigger() {
        return trigger;
    }

    public void setTrigger(boolean trigger) {
        this.trigger = trigger;
    }

    //constructor que invoca al constructor de la clase abstracta GameObject
    public Crosshair(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        firerate = new Chronometer();
        shootSound = new Sound(Assets.playerShoot);
        trigger = false;
    }

    // Obtenemos el centro de la reticula para disparar. Coordenada desde 0,0.
    public Vector2D getCenter() {
        return new Vector2D(position.getX() + width / 2, position.getY() + height / 2);
    }

    @Override
    public void update() {
        // Control de tiempo
        // Almacenamos el tiempo que ha pasado desde la última vez que hemos entrado
//        time += System.currentTimeMillis() - lastTime;
//        lastTime = System.currentTimeMillis();

        if (KeyBoard.PAUSE) {
            if (!pauseG) {
                gameState.pause(pauseG);
                System.out.println("Pausa Crosshair");
                pauseG = true;
            } else {

            }

        }
        if (KeyBoard.PAUSE) {
            if (!pauseG) {
                pauseG = true;
            } else {
                pauseG = false;
                System.out.println("Reanudar");
            }
            gameState.pause(pauseG);
        }

        if (!pauseG) {
            // Control de teclas
            if (!trigger && MouseInput.MLB && !firerate.isRunning()) { // Si pulsamos click y ha pasado mas de 100ms
                trigger = true;
                shootSound.play();
                firerate.run(Constants.FIRERATE);
            }
            if (trigger && !firerate.isRunning() && !MouseInput.MLB) {
                trigger = false;
            }
            // Controlamos que soltamos el gatillo antes de volver a disparar
            position.setX(MouseInput.x);
            position.setY(MouseInput.y);
            // Si el sonido de disparo supera este valor, lo pararemos (así nos evitamos que se solape).
            if (shootSound.getFramePosition() > 8500) {
                shootSound.stop();
            }

//        // Cuando pulsemos la tecla UP estaremos acelerando la nave
//        if (KeyBoard.UP) {
//            acceleration = heading.scale(Constants.ACC);
//            accelerating = true;
//        } else {
//            accelerating = false;
//            // Comprobamos que la magnitud de la velocidad no sea 0
//            if (velocity.getMagnitude() != 0) {
//                acceleration = (velocity.scale(-1).normalize()).scale(Constants.ACC);
//            }
//        }
            // Actualizamos el control de tiempo del disparo
            firerate.update();
        }
    }

    // Devolvemos la nave al punto de reaparición.
    public void resetValues() {
        angle = 0;
        velocity = new Vector2D();
        position = new Vector2D(Constants.WIDTH / 2 - Assets.player.getWidth() / 2, Constants.HEIGHT - 150);
    }

    @Override
    public void draw(Graphics g) {

        Graphics2D g2d = (Graphics2D) g;
        // Pintamos la reticula
        at = AffineTransform.getTranslateInstance(MouseInput.x - Assets.player.getWidth() / 2, MouseInput.y - Assets.player.getHeight() / 2);
        // Pintamos la reticula
        g2d.drawImage(texture, at, null);
    }

}
