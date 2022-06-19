/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duck_hunt.gameObjects;

/**
 *
 * @author elale
 */
public class Constants {

//    //--------------------- Paridas --------------------------------
//    //Frame dimensions
//    //Alto y ancho de la ventana, 1000*600
//    public static final int WIDTH = 1920;
//    public static final int HEIGHT = 980;
//
//    //Player properties
//    //Velocidad de disparo, 300
//    public static final int FIRERATE = (int)Math.random()*100;
//    // Angulo de rotacion de la nave, 0.1
//    public static final double DELTAANGLE = Math.random();
//    // Constante de aceleración, 0.2
//    public static final double ACC = Math.random();
//    // Velocidad máxima, 7
//    public static final double PLAYER_MAX_VEL = Math.random()*10;
//
//    //Laser properties
//    //Velocidad del laser, 15
//    public static final double LASER_VEL = Math.random()*10;
    //--------------------- Serio --------------------------------
    //Frame dimensions
    //Alto y ancho de la ventana, 1000*600
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;

    //Player properties
    //Velocidad de disparo, 300
    public static final int FIRERATE = 500;
    // Angulo de rotacion de la nave, 0.1
    public static final double DELTAANGLE = 0.1;
    // Constante de aceleración, 0.2
    public static final double ACC = 0.2;
    // Velocidad máxima, 7
    public static final double PLAYER_MAX_VEL = 7;

    //Laser properties
    //Velocidad del laser, 15
    public static final double LASER_VEL = 15;

    public static final int ANIMATION_VEL = 50;
    public static final int ENEMY_SCORE = 50;
    public static final int LIVES = 3;
    public static final long FLICKER_TIME = 200;
    public static final long SPAWNING_TIME = 3000;
    public static final int LOADING_BAR_WIDTH = 500;
    public static final int LOADING_BAR_HEIGHT = 50;
    public static final String PLAY = "JUGAR";
    public static final String EXIT = "SALIR";
}
