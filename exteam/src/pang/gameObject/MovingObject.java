
package pang.gameObject;

import bubble_shooter.states.MenuState;
import bubble_shooter.states.State;
import pang.graphics.Assets;
import pang.graphics.Sound;
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import pang.math.Vector2D;
import pang.states.GameState;
/*
 * Esta clase, que extiende de GameObject, tendrá propiedades de todos los objetos que se mueven.
 */
public abstract class MovingObject extends GameObject {

    // Velocidad del objeto.
    protected Vector2D velocity;
    // Rotación del objeto.
    protected AffineTransform at;
    // Representa hacia dónde está apuntando el objeto.
    protected double angle;
    // Representa el máximo de velocidad que puede alcanzar un objeto.
    protected double maxVel;
    // Variables de alto y ancho del objeto.
    protected int width;
    protected int height;
    protected GameState gameState;
    private Sound explosion;


    public MovingObject(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
        super(position, texture);
        this.velocity = velocity;
        this.maxVel = maxVel;
        width = texture.getWidth();
        height = texture.getHeight();
        angle = 0;
        this.gameState = gameState;
//        Inicializamos el sonido de la explosión
        explosion = new Sound(Assets.explosion);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
    }
    // Obtenemos el centro de la nave. Lo utilizaremos a la hora de disparar y también en las colisiones.

    // Metodo para obtener el centro del objeto movible
    protected Vector2D getCenter() {
        return new Vector2D(position.getX() + width / 2, position.getY() + height / 2);
    }

    // Metodo de deteccion de colisiiones
    protected void collidesWith() {
        // Reuperamos el array de objetos movibles
        ArrayList<MovingObject> movingObjects = gameState.getMovingObjects();
        // Iteramos la lista de objetos movibles
        for (int i = 0; i < movingObjects.size(); i++) {

            MovingObject m = movingObjects.get(i);
            // Controlamos que los objetos del mismo tipo no colisionen entre si
            if (m.equals(this)) {
                continue;
            }
            // Restamos el centro del objeto iterado con el centro del objeto que invoca al metodo y obenemos su magnitud
            double distance = m.getCenter().subtract(getCenter()).getMagnitude();
            // Calculamos si la distancia entre los centros de ambos objetos es menor que la suma de los radios de lso objetos 
            // Ademas comprobamso que el obejto instanciasdo esta contenido en el arryay de obejtos 
            if (distance < m.width / 2 + width / 2 && movingObjects.contains(this)) {
                objectCollision(m, this);
            }
        }
    }

    // Control de colisiones , destruccion de objetos colisionados
    private void objectCollision(MovingObject a, MovingObject b) {
        // Si colisionan dos objetos , se destruyen , excepto sin ambos son meteoros
        // Si ninguno de los dos obejtos son meteoros ni están reapariciendo 
        if (a instanceof Player && ((Player) a).isSpawning()) {
            return;
        }
        if (b instanceof Player && ((Player) b).isSpawning()) {
            return;
        }
        if (!(a instanceof meteor && b instanceof meteor)) {
            gameState.playExplosion(getCenter());
           
//            sonido explosiones
            a.destroy();
            b.destroy();
        }

    }

    //Metodo que elimina del array de objetosMovibles el obejto que lo invoque 
    protected void destroy() {
        gameState.getMovingObjects().remove(this);
        
        if ( !(this instanceof Laser )) {
                    explosion.play();        
        }
 
        
    }
    
        
    public void volverM(){
        State.changeState(new MenuState());
    }
}
