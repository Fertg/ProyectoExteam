/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duck_hunt.gameObjects;
 
import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import duck_hunt.math.Vector2D;
import duck_hunt.states.GameState;

/**
 *
 * @author elale
 */
public class MovingObject extends GameObject {

    // Velocidad del objeto
    protected Vector2D velocity;
    // Rotacion del objeto
    protected AffineTransform at;
    // Ángulo de apuntado del objeto
    protected double angle;
    // Representa la velocidad máxima de todos los objetos movibles
    protected double maxVel;
    // Atributo gameState para poder acceder al arrayList
    protected GameState gameState;


    // Variables que nos indican el ancho y alto del objeto
    protected int width, height;

    // Constructor de objetos moviles
    public MovingObject(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
        super(position, texture);
        this.velocity = velocity;
        this.maxVel = maxVel;
        width = texture.getWidth();
        height = texture.getHeight();
        angle = 0;
        this.gameState = gameState;
    }

    // Obtenemos el centro de la nave para disparar y para las colisiones. Coordenada desde 0,0.
    protected Vector2D getCenter() {
        return new Vector2D(position.getX() + width / 2, position.getY() + height / 2);
    }

    // Método de detección de colisiones
    protected void collidesWith() {
        ArrayList<MovingObject> movingObjects = gameState.getMovingObjects();
        for (int i = 0; i < movingObjects.size(); i++) {
            MovingObject m = movingObjects.get(i);
            if (m.equals(this)) {
                continue;
            }
            // Restamos el centro del objeto iterado con el centro del objeto que invoca al método y obtenemos su magnitud
            double distance = m.getCenter().subtract(getCenter()).getMagnitude();
            // Calculamos si la distancia entre los centros de ambos objetos es menor a la suma de sus radios
            // Además, comprobamos que el obbjeto instanciado todavía está contenido en el array
            if (distance < m.width / 2 + width / 2 && movingObjects.contains(this)) {
                // Destruimos los objetos siempre y cuando no sean meteoros
                objectCollision(m, this);
            }
        }
    }

    // Control de colisiones, destrucción de objetos colisionados
    private void objectCollision(MovingObject a, MovingObject b) {
        // Si el objeto a es de tipo jugador y estamos reapareciendo, no colisionamos.
        if ((a instanceof Player && ((Player) a).isSpawning())
                || (b instanceof Player && ((Player) b).isSpawning())) {
            return;
        }
        // Si colisionan dos objetos se destruyen, excepto si ambos son meteoros
        // Si ninguno de los dos objetos son enemigos, serán eliminados
        if (!(a instanceof Enemy && b instanceof Enemy) || !(b instanceof Enemy && a instanceof Enemy)) {
            gameState.playExplosion(position);
            a.destroy();
            b.destroy();
        }
    }

    // Método que elimina del array de objetos movibles el objeto invocador
    public void destroy() {
        gameState.getMovingObjects().remove(this);
    }

    @Override
    public void update() {
    }

    @Override
    public void draw(Graphics g) {
    }

}
