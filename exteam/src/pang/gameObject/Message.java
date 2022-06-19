/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pang.gameObject;

import pang.graphics.Text;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import pang.math.Vector2D;
import pang.states.GameState;

/**
 *
 * @author Fernando
 */

//Clase para mostrar mensajes en la pantalla
public class Message {
//    Indica la transparencia 1 se ve , 0 no se ve

    private float alpha;
//    Cadena texto
    private String text;
//    Posicion del texto
    private Vector2D position;
//Color del Texto
    private Color color;
//    Indica si queremos pintar el texto centrado
    private boolean center;
//    Efecto de desaparecer
    private boolean fade;
//    Fuente de Texto
    private Font font;
//    Variable delta que nos icara el tiempo q tardara en aparecer
    private final float deltaAlpha = 0.01f;
    private GameState gameState;

    public Message(Vector2D position, boolean fade, String text, Color color,
            boolean center, Font font, GameState gameState) {
        this.font = font;
        this.text = text;
        this.position = position;
        this.fade = fade;
        this.color = color;
        this.center = center;
        this.gameState = gameState;

        if (fade) {
            alpha = 1;
        } else {
            alpha = 0;
        }

    }

//Aplicamos la transparencia al objeto grafico
    public void draw(Graphics2D g2d) {
//	Aplicamos la transparencia al objeto graphics	
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
//	Pintamos el texto	
        Text.drawText(g2d, text, position, center, color, font);
//	Aplicamos la opacidad al texto	
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1));
//	Actualizamos la funcion alpha en funcion de si queremos que el texto aparezca o desaparezca	

//Ajustamos la posicion del texto
        position.setY(position.getY() - 1);

        if (fade) {
            alpha -= deltaAlpha;
        } else {
            alpha += deltaAlpha;
        }
        
        if(fade && alpha <0){
            gameState.getMessages().remove(this);
        }

        if (!fade && alpha > 1) {
            fade = true;
            alpha = 1;
        }

    }

}
