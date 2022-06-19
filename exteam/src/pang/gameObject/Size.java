/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pang.gameObject;

import pang.graphics.Assets;
import java.awt.image.BufferedImage;

/**
 *
 * @author Fernando
 */

//Clase tipo enum para controlar diferentes contastantes de los meteoros
public enum Size {
    //Constante de los nombres de las constante.
    BIG (2,Assets.bigs),MED(2,Assets.smalls),SMALL(2,Assets.tinies),TINY(0,null);
    
    //Cantidad de meteoros posibles.
    public int quantity;
    
    //imagenes del meteoro segun su tipo
  
    public BufferedImage[] textures;
    
    
    private Size(int quantity, BufferedImage[] textures){
        this.quantity=quantity;
        this.textures=textures;
        
    }
}
