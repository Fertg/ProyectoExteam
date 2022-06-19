package duck_hunt.states;

import java.awt.Graphics;

/**
 * Clase abstracta State.
 * 
 * @author elale
 */
public abstract class State {
    
    // Atributo que nos indica el estado actual del juego (menu o jugando).
    private static State currentState = null;
    
    // Atributo para guardar el usuario del juego
    public static String user;

    // Retornamos el esstado del juego actual.
    public static State getCurrentState() {
        return currentState;
    }
    
    // cambiamos el estado del juego.
    public static void changeState(State newState){
        currentState = newState;
    }
 
    // Métodos abstractos.
    public abstract void update();
    public abstract void draw(Graphics g);
    
}
