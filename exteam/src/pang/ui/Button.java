/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pang.ui;

import pang.graphics.Assets;
import pang.graphics.Text;
import pang.input.MouseInput;
import java.awt.Color;

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import pang.math.Vector2D;

/**
 *
 * @author Fernando
 */
public class Button {

//Efecto hover botones
    private BufferedImage mouseOutImg;
    private BufferedImage mouseInImg;

//    Nos indica si el raton esta encima del boton
    private boolean mouseIn;
//    Rectangulo
    private Rectangle boundingBox;
//    Acción  del boton
    private Action action;
//    Texto del boton
    private String text;

    public Button(
            BufferedImage mouseOutImg,
            BufferedImage mouseInImg,
            int x, int y,
            String text,
            Action action
    ) {
        this.mouseInImg = mouseInImg;
        this.mouseOutImg = mouseOutImg;
        this.text = text;
        boundingBox = new Rectangle(x, y, mouseInImg.getWidth(), mouseInImg.getHeight());
        this.action = action;
    }

    public void update() {

        if (boundingBox.contains(MouseInput.X, MouseInput.Y)) {
            mouseIn = true;
        } else {
            mouseIn = false;
        }
//Si esta dentro y hemos pulsado el boton izquierdo
        if (mouseIn && MouseInput.MLB) {
//   Ejecutamos la accion vinculada al boton
            action.doAction();
        }
    }

    public void draw(Graphics g) {

        if (mouseIn) {
            g.drawImage(mouseInImg, boundingBox.x, boundingBox.y, null);
        } else {
            g.drawImage(mouseOutImg, boundingBox.x, boundingBox.y, null);
        }

        Text.drawText(
                g,
                text,
                new Vector2D(
                        boundingBox.getX() + boundingBox.getWidth() / 2,
                        boundingBox.getY() + boundingBox.getHeight()),
                true,
                Color.BLACK,
                Assets.fontMed);

    }

}
