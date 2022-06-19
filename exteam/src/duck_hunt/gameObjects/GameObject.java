/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duck_hunt.gameObjects;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import duck_hunt.math.Vector2D;

/**
 *
 * @author elale
 * Clase abstracta que contiene las propiedades comunes de los objetos del juego
 */
public abstract class GameObject {
    // Protected porque solo es accedido por miembros de su misma clase
    protected BufferedImage texture;
    // Vector que nos indica la posición del objeto en base al sistema de coordenadas
    protected Vector2D position;

    
    // Constructor parametrizado de la clase 
    public GameObject(Vector2D position, BufferedImage texture) {
        this.position = position;
        this.texture = texture;
    }

    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }  
    
     // Actualiza
    public abstract void update();

    // Dibujamos el objeto Graphics
    public abstract void draw(Graphics g);
    
}
