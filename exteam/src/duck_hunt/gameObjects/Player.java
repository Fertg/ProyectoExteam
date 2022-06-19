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
public class Player extends MovingObject {

    // Vector que indica hacia dónde está mirando la nave.
    private Vector2D heading;
    // Representa la aceleracion de la navae
    private Vector2D acceleration;
    // Bandera para saber cuando estamos acelerando
    private boolean accelerating = false;
    // Creamos un chrono
    private Chronometer firerate, spawnTime, flickerTime;
    // Spawn y visible
    private boolean spawning, visible;
    // Sonido disparo
    private Sound shoot;
    //PAUSE
    private boolean pause=false;
    //constructor que invoca al constructor de la clase abstracta GameObject
    public Player(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        this.gameState = gameState;
        heading = new Vector2D(0, 1);
        acceleration = new Vector2D();
        firerate = new Chronometer();
        spawnTime = new Chronometer();
        flickerTime = new Chronometer();
        shoot = new Sound(Assets.playerShoot);

    }

    // Obtenemos el centro de la nave para disparar y para las colisiones. Coordenada desde 0,0.
    public Vector2D getCenter() {
        return new Vector2D(position.getX() + width / 2, position.getY() + height / 2);
    }

    @Override
    public void update() {

        if (!spawnTime.isRunning()) {
            spawning = false;
            visible = true;
        }

        if (spawning && !flickerTime.isRunning()) {
            flickerTime.run(Constants.FLICKER_TIME);
            visible = !visible;
        }

        // Control de tiempo
        // Almacenamos el tiempo que ha pasado desde la última vez que hemos entrado
//        time += System.currentTimeMillis() - lastTime;
//        lastTime = System.currentTimeMillis();
        // Control de teclas
        if (KeyBoard.SHOOT && !firerate.isRunning() && !spawning) { // Si pulsamos espacio y ha pasado mas de 100ms
            // Creamos un laser y lo añadimos al array de objetos movibles
            gameState.getMovingObjects().add(0, new Laser(
                    getCenter().add(heading.scale(width)), // Vector de posicion del disparo
                    heading, // velocidad
                    Constants.LASER_VEL, // Velocidad máxima
                    angle, // Ángulo
                    Assets.redLaser, // Imagen
                    gameState
            ));
            shoot.play();
            firerate.run(Constants.FIRERATE);
        }
        
        // Si el sonido de disparo supera este valor, lo pararemos (así nos evitamos que se solape).
        if (shoot.getFramePosition() > 8500) {
            shoot.stop();
        }

        if (KeyBoard.RIGHT) {
            // Rotamos 18 grados a la derecha: Pi en radianes, son 180 grados, así que 180/10 son 18 grados.
            angle += Constants.DELTAANGLE;
        }
        if (KeyBoard.LEFT) {
            // Rotamos 18 grados a la derecha: Pi en radianes, son 180 grados, así que 180/10 son 18 grados.
            angle -= Constants.DELTAANGLE;
        }

        // Cuando pulsemos la tecla UP estaremos acelerando la nave
        if (KeyBoard.UP) {
            acceleration = heading.scale(Constants.ACC);
            accelerating = true;
            System.out.println("entra");
        } else {
            accelerating = false;
            // Comprobamos que la magnitud de la velocidad no sea 0
            if (velocity.getMagnitude() != 0) {
                acceleration = (velocity.scale(-1).normalize()).scale(Constants.ACC);
            }
        }
//           //PAUSA DEL JUEGO. PAUSAR , SE REANUDA EN EL GAMESTATE
//           if(KeyBoard.PAUSE){
//               
//               if(!pause){
//                   pause=true;         
//                 
//               }else{
//                   System.out.println("Entraaa");
//                   pause=false;
//               }
//               gameState.pause(pause);
//           
//        }

        // Rotamos la nave 90 grados ya que la imagen aparece rotada -90 grados de inicio
        heading = heading.setDirection(angle - Math.PI / 2);

        // La velocidad es representada como el cambio en la posicion respecto al tiempo
        position = position.add(velocity);

        // La aceleración representa el cambio de posicion respecto al tiempo
        velocity = velocity.add(acceleration);

        // Limitamos la velocidad de la nave con la velocidad máxima definida en GameState
        velocity = velocity.limit(maxVel);

        if (position.getX() > Constants.WIDTH) {
            position.setX(-width);
        }
        if (position.getY() > Constants.HEIGHT) {
            position.setY(-height);
        }
        if (position.getX() < -width) {
            position.setX(Constants.WIDTH);
        }
        if (position.getY() < -height) {
            position.setY(Constants.HEIGHT);
        }
        // Actualizamos el control de tiempo del disparo
        firerate.update();
        spawnTime.update();
        flickerTime.update();
        //Detectamos colisiones
        collidesWith();
    }

    @Override
    public void destroy() {
        spawning = true;
        spawnTime.run(Constants.SPAWNING_TIME);
        resetValues();
        gameState.substractLife();
    }

    // Retornamos verdadero si estamos reapariciendo.
    public boolean isSpawning() {
        return spawning;
    }

    // Devolvemos la nave al punto de reaparición.
    public void resetValues() {
        angle = 0;
        velocity = new Vector2D();
        position = new Vector2D(Constants.WIDTH / 2 - Assets.player.getWidth() / 2, Constants.HEIGHT - 150);
    }

    @Override
    public void draw(Graphics g) {

        if (!visible) {
            return;
        }

        Graphics2D g2d = (Graphics2D) g;

        // Pintamos la propulsion
        // Creamos dos elementos AffineTransform para los fuegos de los propulsores
        // Pintamos el motor derecho comenzando a pintar desde el centro de la nave
        AffineTransform motorDerecho = AffineTransform.getTranslateInstance(position.getX() + width / 2 + 5, position.getY() + height / 2 + 10);
        AffineTransform motorIzquierdo = AffineTransform.getTranslateInstance(position.getX() + 5, position.getY() + height / 2 + 10);

        motorDerecho.rotate(angle, -5, -10);
        motorIzquierdo.rotate(angle, width / 2 - 5, -10);

        if (accelerating) {
            g2d.drawImage(Assets.speed, motorDerecho, null);
            g2d.drawImage(Assets.speed, motorIzquierdo, null);

        }

        // Pintamos la nave
        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        //Damos ángulo y ponemos el eje de rotación en el centro de la nave
        at.rotate(angle, width / 2, height / 2);
        // Pintamos la nave rotada
        g2d.drawImage(texture, at, null);
    }
}
