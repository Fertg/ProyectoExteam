package pang.gameObject;

import bubble_shooter.states.MenuState;
import bubble_shooter.states.State;
import pang.graphics.Assets;
import pang.graphics.Sound;
import pang.input.KeyBoard;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import javax.swing.JOptionPane;
import pang.main.WindowPang;
import pang.math.Vector2D;
import pang.states.GameState;
//Clase jugador que extiende de Moving ObJECTO

public class Player extends MovingObject {

    // Indica hacia dónde está mirando la nave.
    private Vector2D heading;
    // Vector que representa la aceleración. Cambio de velocidad con respecto al tiempo.
    private Vector2D aceleration;

    // Para saber cuándo estamos acelerando.
    private boolean acelerando = false;
    // Accedemos a esta clase.
    int j = 0;
    private Chronometer firerate;

    private Chronometer spawnTime, flickerTime;
    private WindowPang wp;
    private boolean spawning, visible;
    private boolean pause = false;

    private Sound shoot, loose;
    int i = Constants.WIDTH / 2;

    public Player(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        this.gameState = gameState;
        heading = new Vector2D(0, 1);
        aceleration = new Vector2D();
        firerate = new Chronometer();
//        Cronometro de reaparicion
        spawnTime = new Chronometer();
        flickerTime = new Chronometer();

//        Inicializamosel sonido
        shoot = new Sound(Assets.playerShoot);
        loose = new Sound(Assets.playerLoose);
    }

    @Override
    public void update() {
        //Se incova este update desde GameState para actualizar la psoicion de la nave
// Se habra pulsado una tecla
// Control de tiempos de reaparicion 
//Si el cronometro de reaparición no está corriendo , la variable reaparición es falsa y visible true
//Por que la nave será visible
        if (!spawnTime.isRunning()) {
            spawning = false;
            visible = true;
        }
//Si estamos en reaparicion y el cronometro de tiempo de reaparicion no esta corriendo , ponemos a true el visible
        if (spawning) {

            if (!flickerTime.isRunning()) {
//Si el cronometro no esta corriendo , entonces arrancamos 
                flickerTime.run(Constants.FLICKER_TIME);
                visible = !visible;

            }

        }

        if (KeyBoard.RIGHT) {

            position.setX(i++);
            position.setX(i++);
            position.setX(i++);

        }
        if (KeyBoard.LEFT) {
            position.setX(--i);
            position.setX(--i);
            position.setX(--i);
        }
     

        // la acceleracion representa el cambio de posicion respecto al tiempo 
        // por tanto , sumamos al vector velocidad al vector acceleracion
        velocity = velocity.add(aceleration);
        //Rotamos la imagen 90 gardos yq eu aparece rotada de inicio
        heading = heading.setDirection(angle - Math.PI / 2);
        // La velocidad es representada como el cambio en la posicion respecto al tiempo 
        // Cada fotograma le sumamos al vector posicion el vector velocidad 
        position = position.add(velocity);

        //Limitams la velocidad  maxima
        velocity = velocity.limit(maxVel);

        if (position.getX() > Constants.WIDTH) {
            position.setX(0);
            i = 0;

        }
        if (position.getY() > Constants.HEIGHT) {
            position.setY(0);
        }
        if (position.getX() < -width) {

            position.setX(Constants.WIDTH);
            i = Constants.WIDTH;
        }
        if (position.getY() < -height) {
            position.setY(Constants.HEIGHT);
        }
        if (KeyBoard.SHOOT && !firerate.isRunning() && !spawning) {
            gameState.getMovingObjects().add(0, new Laser(
                    getCenter().add(heading.scale(width)),
                    heading,
                    Constants.LASER_VEL,
                    angle,
                    Assets.redLaser,
                    gameState
            ));
            firerate.run(Constants.FIRERATE);
//     Sonido de disparo       

            shoot.play();
        }

//            Acortar el sonido para solucionar el problema del disparo y el tiempo de reproduccion
        if (shoot.getFramePosition() > 7500) {
            shoot.stop();
        }
        //Deteccion de colisiones
        collidesWith();
        // Actualizamos el control del tiempo del disparo
        firerate.update();

//        actualizamos los tiempos de reaparici´n
        spawnTime.update();
        flickerTime.update();

    }

    @Override
    public void draw(Graphics g) {
//        GAME OVER
        if (!gameState.gameOver) {
            if (!visible) {
                return;
            }

            Graphics2D g2d = (Graphics2D) g;

            // Obtenemos el elemento que utilizaremos para rotar
            at = AffineTransform.getTranslateInstance(position.getX(), position.getY());

            //ANIMACIÓN PARA QUE SE MUEVA EL PLAYER SEGÚN LA TECLA QUE PULSEMOS
            if (KeyBoard.LEFT) {

                if (j == 0) {
                    g2d.drawImage(Assets.ArrayMovIzq[0], at, null);
                    j++;
                } else if (j == 1) {
                    g2d.drawImage(Assets.ArrayMovIzq[0], at, null);
                    j++;

                } else if (j == 2) {
                    g2d.drawImage(Assets.ArrayMovIzq[0], at, null);
                    j++;
                } else if (j == 3) {
                    g2d.drawImage(Assets.ArrayMovIzq[1], at, null);
                    j++;
                } else if (j == 4) {
                    g2d.drawImage(Assets.ArrayMovIzq[1], at, null);
                    j = 0;
                } else {
                    g2d.drawImage(Assets.ArrayMovIzq[1], at, null);
                }
            } else if (KeyBoard.RIGHT) {
                if (j == 0) {
                    g2d.drawImage(Assets.ArrayMovDer[0], at, null);
                    j++;
                } else if (j == 1) {
                    g2d.drawImage(Assets.ArrayMovDer[0], at, null);
                    j++;

                } else if (j == 2) {
                    g2d.drawImage(Assets.ArrayMovDer[0], at, null);
                    j++;
                } else if (j == 3) {
                    g2d.drawImage(Assets.ArrayMovDer[1], at, null);
                    j++;
                } else if (j == 4) {
                    g2d.drawImage(Assets.ArrayMovDer[1], at, null);
                    j = 0;
                } else {
                    g2d.drawImage(Assets.ArrayMovDer[1], at, null);
                }

            } else {
                g2d.drawImage(Assets.player, at, null);
            }
        } else {
            gameState.getMovingObjects().remove(this);

        }
    }

    @Override
    protected void destroy() {
        spawning = true;
        spawnTime.run(Constants.SPAWNING_TIME);
        gameState.subtractLife();
        loose.play();
//        Resetear valores para que nos pinte la nave en el medio.
        resetValues();
    }

//    Para saber si estamos reapareciendo
    public boolean isSpawning() {
        return spawning;
    }
//Reseteamos valores de posicion de la nave para reaparecer tras ser eliminado.

    private void resetValues() {

        angle = 0;
        velocity = new Vector2D();
        position = new Vector2D(Constants.WIDTH / 2 - Assets.player.getWidth() / 2,
                Constants.HEIGHT - Assets.player.getHeight() - 25);
    }
}
