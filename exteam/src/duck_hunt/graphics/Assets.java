/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duck_hunt.graphics;

import java.awt.Font;
import java.awt.image.BufferedImage;
import javax.sound.sampled.Clip;

/**
 *
 * @author elale Clase que crea objetos imagenes. Las imagenes las guardaremos en la carpeta 'res'
 */
public class Assets {

    // Creamos el objeto con la imagen del jugador
    public static BufferedImage player;
    // Creamos el objeto con la imagen de la propulsión
    public static BufferedImage speed;
    // Creamos los objetos con las imagenes de los lásers
    public static BufferedImage redLaser, greenLaser, blueLaser;
    // Creamos los objetos con las imagenes de los enemigos
    public static BufferedImage blackEnemy, blueEnemy, greenEnemy, redEnemy;
    public static BufferedImage[] enemies = new BufferedImage[6];
    //Lista de explision
    public static BufferedImage[] arrayFramesExp = new BufferedImage[9];
    // Lista con los números del 0 al 9 y el símbolo X.
    public static BufferedImage[] numbers = new BufferedImage[11];
    // Imagen para las vidas.
    public static BufferedImage live;
    // Fuentes.
    public static Font fontBig;
    public static Font fontMed;
    // Sonidos.
    public static Clip backgroundMusic, explosion, playerLoose, playerShoot, game_over, levelUp;
    public static BufferedImage blueBtn;
    public static BufferedImage greyBtn;
    public static BufferedImage background0;
    public static BufferedImage duck;
    public static BufferedImage fondo;
    public static BufferedImage caseta;
    public static BufferedImage agua;
    public static BufferedImage agua2;
    
    public static BufferedImage duck_outline_brown, duck_outline_white, duck_outline_yellow;
    public static BufferedImage duck_outline_target_brown, duck_outline_target_white, duck_outline_target_yellow;

    // Contador
    public static int count = 0;
    public static boolean loader = false;
    public static final float MAX_COUNT = 44;

    // Se invoca cada vez que el juego arranque
    public static void init() {
         duck=Loader.imageLoader("/res/duck_hunt/background/duckback.png");
        // Asignamos a nuestro objeto player la imagen que queremos cargar.
        player = imageLoader("/res/duck_hunt/others/crosshair_outline_large.png");
        speed = imageLoader("/res/duck_hunt/effects/fire08.png");
        redLaser = imageLoader("/res/duck_hunt/lasers/laserRed01.png");
        greenLaser = imageLoader("/res/duck_hunt/lasers/laserGreen11.png");
        blueLaser = imageLoader("/res/duck_hunt/lasers/laserBlue01.png");

        duck_outline_brown = imageLoader("/res/duck_hunt/enemies/duck_outline_brown.png");
        duck_outline_white = imageLoader("/res/duck_hunt/enemies/duck_outline_white.png");
        duck_outline_yellow = imageLoader("/res/duck_hunt/enemies/duck_outline_yellow.png");
        duck_outline_target_brown = imageLoader("/res/duck_hunt/enemies/duck_outline_target_brown.png");
        duck_outline_target_white = imageLoader("/res/duck_hunt/enemies/duck_outline_target_white.png");
        duck_outline_target_yellow = imageLoader("/res/duck_hunt/enemies/duck_outline_target_yellow.png");
        caseta = imageLoader("/res/duck_hunt/others/telon.png");
        agua = imageLoader("/res/duck_hunt/others/agua.png");
        agua2 = imageLoader("/res/duck_hunt/others/agua2.png");

        live = imageLoader("/res/duck_hunt/others/icon_duck.png");
        backgroundMusic = loadSound("/res/duck_hunt/sounds/backgroundMusic.wav");
        explosion = loadSound("/res/duck_hunt/sounds/explosion.wav");
        playerLoose = loadSound("/res/duck_hunt/sounds/playerLoose.wav");
        playerShoot = loadSound("/res/duck_hunt/sounds/playerShoot.wav");
        game_over = loadSound("/res/duck_hunt/sounds/game_over.wav");
        levelUp = loadSound("/res/duck_hunt/sounds/levelUp.wav");
        blueBtn = imageLoader("/res/duck_hunt/ui/blue_button.png");
        greyBtn = imageLoader("/res/duck_hunt/ui/grey_button.png");
        background0 = imageLoader("/res/duck_hunt/background/background0.png");
        fondo=imageLoader("/res/duck_hunt/background/patos.jpeg");
        // No los he cargado directamente por si quiero utilizar uno de manera individual en un futuro
        enemies[0] = duck_outline_brown;
        enemies[1] = duck_outline_white;
        enemies[2] = duck_outline_yellow;
        enemies[3] = duck_outline_target_brown;
        enemies[4] = duck_outline_target_white;
        enemies[5] = duck_outline_target_yellow;
        for (int i = 0; i < arrayFramesExp.length; i++) {
            arrayFramesExp[i] = imageLoader("/res/duck_hunt/explosion/" + i + ".png");
        }
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = imageLoader("/res/duck_hunt/numbers/" + (i) + ".png");
        }
        fontBig = loadFont("/res/duck_hunt/fonts/kenney_blocks.ttf", 42);
        fontMed = loadFont("/res/duck_hunt/fonts/kenney_blocks.ttf", 20);
        loader = true;
    }

    public static BufferedImage imageLoader(String path) {
        count++;
        for (int i = 0; i < 10; i++) {
            Loader.imageLoader(path);
        }
        return Loader.imageLoader(path);
    }

    public static Clip loadSound(String path) {
        count++;
        for (int i = 0; i < 10; i++) {
            Loader.loadSound(path);
        }
        return Loader.loadSound(path);
    }

    public static Font loadFont(String path, int size) {
        count++;
        for (int i = 0; i < 10; i++) {
            Loader.loadFont(path, size);
        }
        return Loader.loadFont(path, size);
    }
}
