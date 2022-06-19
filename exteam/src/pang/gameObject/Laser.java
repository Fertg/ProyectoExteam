/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pang.gameObject;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import pang.math.Vector2D;
import pang.states.GameState;
/**
 *
 * @author Fernando
 */
//Clase para crear los lasers, tanto del UFO como de la nave
public class Laser extends MovingObject {

    // Como la dirección del láser es la misma que la del jugador, vamos a pasar el ángulo también como parámetro al constructor.
    // Además, el vector velocidad del láser va a ser el mismo que el vector heading en la clase Player en el momento que presionamos la tecla disparar. 
    // La velocidad será igual a la velocidad de la nave escalada a la velocidad máxima.
    public Laser(Vector2D position, Vector2D velocity, double maxVel, double angle, BufferedImage texture, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        this.angle = angle;
        this.velocity = velocity.scale(maxVel);
    }

    @Override
    public void update() {
        // l veloidad en este caso es constante
        position = position.add(velocity);

        if (position.getX() < 0 || position.getX() > Constants.WIDTH
                || position.getY() < 0 || position.getY() > Constants.HEIGHT) {
            gameState.getMovingObjects().remove(this);
        }
        //Deteccion de colisiones
        collidesWith();
    }
//Pintamos el laser
    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        at = AffineTransform.getTranslateInstance(position.getX() - width / 2, position.getY());

        at.rotate(angle, width / 2, 0);

        g2d.drawImage(texture, at, null);
    }

    @Override
    protected Vector2D getCenter() {
        return new Vector2D(position.getX() + width / 2, position.getY() + width / 2);
    }

    // Obtenemos el centro de la nave. Lo utilizaremos a la hora de disparar y también en las colisiones.
}
