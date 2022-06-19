package pang.input;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

//Clase para controlar las acciones del teclado.
public class KeyBoard implements KeyListener{
    // Creamos un array de booleanos con un tamaño de 256 que es el valor máximo del número de las teclas del teclado.
    // Con este array, podremos asignar funcionalidad a todas las teclas del teclado.
    private boolean[] keys = new boolean[256];
    
    // Vamos a manejar la nave con las flechas. Creamos los atributos booleanos, para saber si se han pulsado.
    // Recuerdo que la posición no se controla en esta clase.
    public static boolean UP, LEFT, RIGHT, SHOOT,PAUSE,EXIT;
    
    public KeyBoard(){
        // Inicializamos las teclas a false porque no están pulsadas.
        UP = false;
        LEFT = false;
        RIGHT = false;        
        SHOOT = false;
        EXIT=false;
        PAUSE = false;
    }
    
    
    public void update(){
        // Actualizamos las teclas con el valor correspondiente de nuestro vector de teclas.
        UP = keys[KeyEvent.VK_UP];
        LEFT = keys[KeyEvent.VK_LEFT];
        RIGHT = keys[KeyEvent.VK_RIGHT];        
        SHOOT = keys[KeyEvent.VK_SPACE];
        PAUSE= keys[KeyEvent.VK_ESCAPE];
        EXIT= keys[KeyEvent.VK_E];
    }
    
    
    
    
    // Cada vez que presionemos una tecla se llamará a este método.
    @Override
    public void keyPressed(KeyEvent e) {
        // La información de la tecla la obtenemos del atribuo keyCode
        // System.err.println(e.getKeyCode());
        
        // Si se pulsa una tecla, indicamos la posición de la misma en nuestro array de teclas a true.
        keys[e.getKeyCode()]=true;
    }

    // Cada vez que soltemos la tecla se llamará a este método.
    @Override
    public void keyReleased(KeyEvent e) {
        // Si se suelta una tecla, indicamos la posición de la misma en nuestro array de teclas a false.
        keys[e.getKeyCode()]=false;
    }

    // Aunque no usemos este método, al ser abstracto tenemos que añadirlo.
    @Override
    public void keyTyped(KeyEvent e) { }
    
}
