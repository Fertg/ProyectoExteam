/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duck_hunt.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;


/**
 *
 * @author elale
 */
public class KeyBoard implements KeyListener{
    
    private boolean[] keys = new boolean[256];
    
    public static boolean UP, LEFT, RIGHT, SHOOT,PAUSE,EXIT;
    
    public KeyBoard(){
        UP = false;
        LEFT = false;
        RIGHT = false;
        SHOOT = false;
        PAUSE=false;
        EXIT=false;
    }

    public void update(){
        UP = keys[KeyEvent.VK_W];
        LEFT = keys[KeyEvent.VK_A];
        RIGHT = keys[KeyEvent.VK_D];
        SHOOT = keys[KeyEvent.VK_SPACE];
         PAUSE= keys[KeyEvent.VK_ESCAPE];
         EXIT= keys[KeyEvent.VK_E];
       
    }
    
    @Override
    public void keyTyped(KeyEvent ke) {
    }

    @Override
    public void keyPressed(KeyEvent ke) {
        keys[ke.getKeyCode()] = true;
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        keys[ke.getKeyCode()] = false;
    }
    
}
