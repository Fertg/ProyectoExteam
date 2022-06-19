
package pang.gameObject;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import pang.math.Vector2D;
/**
 *
 * @author Fernando
 */
/*
 * Clase abstracta, no vamos a crear objetos de esta clase, sino que otras clases heredarán de esta clase.
 */
public abstract class GameObject {
//    Solo es accedido por miembros de la misma clase.
    protected BufferedImage texture;
    
    // 2. El sistema de coordenadas de la ventana parte de la esquina superior izquierda como coordenada 0,0.
    // Desde ahí, hacia abajo y la derecha, va incrementandose la coordenada.
    // NOTA: Vamos a utilizar vectores para la posición, así como para la velocidad, aceleración, etc.
    // Todo ello en la clase Vector2D.
    protected Vector2D position;    
    
    // Inizializamos el objeto
    public GameObject(Vector2D position, BufferedImage texture){
        this.position=position;
        this.texture=texture;
    }
    
    public Vector2D getPosition() {
        return position;
    }

    public void setPosition(Vector2D position) {
        this.position = position;
    }
    
    /* Métodos abstractos*/
    public abstract void update();
    public abstract void draw(Graphics g);
        
    
    
    
}
