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

//Clase que controla los meteoreos extiende de MovingObject
public class meteor extends MovingObject {
    //atributo para saber en cuantos meteoros se divide;

    private Size size;

//    posicion, velocidad, maxima velocidad, imagenes, estado del juegos y tamaño.
    public meteor(Vector2D position, Vector2D velocity, double maxVel, BufferedImage texture, GameState gameState,Size size) {
        super(position, velocity, maxVel, texture, gameState);
        this.size=size;
        this.velocity=velocity;
    }

    //Actualizamos la posicion de los meteoros. Si se salen por la pantalla aparecen
    //por el otro lado
    @Override
    public void update() {
        position = position.add(velocity);
        //si se salen por la pantalla aparecen por el otro lado
        if (position.getX() > Constants.WIDTH) {
            position.setX(0);
        }
        if (position.getY() > Constants.HEIGHT) {
            position.setY(0);
           
        }
        if (position.getX() < 0) {
            position.setX(Constants.WIDTH);
        }
        if (position.getY() < 0) {
            position.setY(Constants.HEIGHT);
        }
        
        angle +=Constants.DELTAANGLE /2;

    }
    
    
//pintamos los meteoros y aplicamos el efecto de rotacion
    @Override
    public void draw(Graphics g) {
        Graphics2D g2D = (Graphics2D) g;
        at = AffineTransform.getTranslateInstance(position.getX(), position.getY());
        at.rotate(angle, width / 2, height / 2);
        //
        g2D.drawImage(texture, at, null);
    }
    
    //retomamos el numero y tipo de meteoros en los que se divide al se eliminado
    public Size getSize(){
        return size;
    }
    
//    Metodo heredado para destruir meteoritos
    @Override
    public void destroy(){
        gameState.divideMeteor(this);
        gameState.addScore(Constants.METEOR_SCORE, position);
        super.destroy();
    }

}
