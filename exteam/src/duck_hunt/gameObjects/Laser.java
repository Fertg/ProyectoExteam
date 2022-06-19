/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duck_hunt.gameObjects;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import duck_hunt.math.Vector2D;
import duck_hunt.states.GameState;

/**
 *
 * @author elale
 */
public class Laser extends MovingObject {

    public Laser(Vector2D position, Vector2D velocity, double maxVel, double angle, BufferedImage texture, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        this.angle = angle;
        this.velocity = velocity.scale(maxVel);
    }

    // Obtenemos el centro de la nave para disparar y para las colisiones. Coordenada desde 0,0.
    protected Vector2D getCenter() {
        return new Vector2D(position.getX() + width / 2, position.getY() + width / 2);
    }

    @Override
    public void update() {
        // La velocidad en este caso es constante.
        // Por tanto, simplemente en la posición le añadimos la velocidad
        position = position.add(velocity);
        if (position.getX() < 0 || position.getX() > Constants.WIDTH || position.getY() < 0 || position.getY() > Constants.HEIGHT) {
            destroy();
        }
        this.collidesWith();
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;

        // Pintamos el laser desde el morro de la nave
        at = AffineTransform.getTranslateInstance(position.getX() - width / 2, position.getY());
        // Ajustamos la rotación
        at.rotate(angle, width / 2, 0);
        //Pintamos la nave
        g2d.drawImage(texture, at, null);

    }

}
