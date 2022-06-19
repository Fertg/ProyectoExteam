/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pang.gameObject;

/**
 *
 * @author Fernando
 */

//Clase con las constantes del Juego
public class Constants {
//Dimensiones de la pantalla
    public static final int WIDTH = 1000;
    public static final int HEIGHT = 600;
//    Velocidad de disparo
    public static final int FIRERATE = 500;
    public static final double DELTAANGLE = 0.1;
//    Aceleracion
    public static final double ACC = 0.2;
//    Max Velocidad
    public static final double PLAYER_MAX_VEL = 7.0;
    public static final double LASER_VEL = 20.0;
//    VELOCIDAD DEL DISPARO DEL UFO
    public static final double UFO_LASER_VEL = 7.0;
    public static final double METEOR_VEL = 2.0;
//    Radio del nodo del camino del ufo 
    public static final int NODE_RADIUS = 200;
//     constant masa para aceleracon 2 ley de Newton
    public static final double UFO_MASS = 60;

    public static final int UFO_MAX_VEL = 8;
    public static final long UFO_FIRE_RATE = 1000;

//    angulo de tiro del UFO
    public static final double UFO_ANGLE_RANGE = Math.PI / 2;
//   Puntuacion por matar un UFO
    public static final int UFO_SCORE = 40;
//    Puntuacion por eliminar un meteoro
    public static final int METEOR_SCORE = 40;
//Tiempo de reaparición en muerte
    public static final long FLICKER_TIME = 200;
    public static final long SPAWNING_TIME = 3000;
//    Texto de los botones
    public static final String PLAY = "JUGAR";

    public static final String EXIT = "SALIR";
//Imagen de la barra
    public static final int LOADING_BAR_WIDTH = 500;
    public static final int LOADING_BAR_HEIGHT = 50;

}
