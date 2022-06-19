package pang.states;

import java.awt.Graphics;

/**
 *
 * @author Fernando
 */

//Clase abstracta de estados del juego
public abstract class State {

//    Atributo que nos indica el estado actual del juego,menu o jugando
    private static State currentState = null;
    public static String user;
    
    
    public static State getCurrentState() {
        return currentState;
    }
//Cambiar de estado
    public static void changeState(State newState) {
        currentState = newState;
    }

    public abstract void update();

    public abstract void draw(Graphics g);
}
