/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duck_hunt.gameObjects;

import duck_hunt.graphics.Assets;
import duck_hunt.graphics.Sound;
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
public class Enemy extends MovingObject {

    // Sonido de explosion.
    private Sound explosion;

    public Enemy(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState) {
        super(position, velocity, maxVel, texture, gameState);
        explosion = new Sound(Assets.explosion);

    }

    @Override
    public void destroy() {
        gameState.addScore(Constants.ENEMY_SCORE, position);
        gameState.getMovingObjects().remove(this);
        explosion.play();
    }

    @Override
    public void update() {
        position = position.add(velocity);
        if (position.getX() > Constants.WIDTH + width) {
            position.setX(-width);
        }
    }

    @Override
    public void draw(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle, width / 2, height / 2);
        g2d.drawImage(texture, at, null);
    }

}
