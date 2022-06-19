package pang.graphics;

import pang.graphics.Loader;

import java.awt.Font;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.sound.sampled.Clip;
import javax.sound.sampled.UnsupportedAudioFileException;

/**
 *
 * @author Fernando
 */
//Clase para guardar recursos externos del juego
public class Assets {

    // Creamos objetos imagenes. Las imagenes las guardaremos en una carpeta dentro del proyecto.
    // Crearemos la carpeta en el proyecto y añadiremos a Sources en las propiedades del proyecto.
    // Dentro de esa carpeta principal de recursos, iremos creando carpetas para cada elemento que vayamos añadiendo al juego.
    // Creamos imagen para jugador.
    public static BufferedImage player;

    // effects
    public static BufferedImage speed;
    public static BufferedImage back_pang;

    // Láseres para disparar
    public static BufferedImage redLaser, blueLaser, greenLaser;

    public static BufferedImage ufo;

    //Lista de meteoros
    public static BufferedImage[] bigs = new BufferedImage[4];
    public static BufferedImage[] meds = new BufferedImage[2];
    public static BufferedImage[] smalls = new BufferedImage[2];
    public static BufferedImage[] tinies = new BufferedImage[2];

//    Lista para explosiones
    public static BufferedImage[] ArrayFrameExp = new BufferedImage[9];
    
    //    Lista para MovimientoIzq
    public static BufferedImage[] ArrayMovIzq= new BufferedImage[2];

   //    Lista para MovimientoD
    public static BufferedImage[] ArrayMovDer= new BufferedImage[2];

    
//    Lista para cargar las imagenes de los numeros
    public static BufferedImage[] numbers = new BufferedImage[11];

//    Imagenes de las vidas
    public static BufferedImage life;
    public static BufferedImage bk;

    // Fonts
    public static Font fontBig;
    public static Font fontMed;

//    Sonidos 
    public static Clip backgroundMusic, explosion, playerLoose, playerShoot, ufoShoot, passLevel;

//    Botonoes UI
    public static BufferedImage blueBtn;
    public static BufferedImage greyBtn;

//    Atributo para controlar la carga de recursos
//    Mientras carga es falso y cuando está cargado se pone a true
    public static boolean loaded = false;
//Contador para rellenar la barra de recursos y saber cuantos se van cargando.
    public static float count = 0;
//    Es el maximo  de recursos a cargar
    public static float MAX_COUNT = 46;
    public static BufferedImage bk3;
    public static BufferedImage png;

    // Se invocará cada vez que el juego arranque.
    public static void init() throws UnsupportedAudioFileException {
        //IMAGEN EN EL MENU DE CARGA
        //Cargamos la imagen directamente con el metodo del Loader para que sea lo primero q se carga
         png=Loader.loadImage("/res/pang/bk/png.jpg");
        // Asignamos a nuestro elemento player la ruta de la imagen que queremos cargar.
        // Ojo, que al haber añadido nuestra carpeta Resources al Build del proyecto, la ruta que indiquemos comienza a partir de ahí, sin incluir la carpeta Resources.
        player = loadImage("/res/pang/ships/player.png");

        // Imagen para el efecto de propulsión de la nave
        speed = loadImage("/res/pang/effects/fire08.png");

        // Imagenes para los disparos
        redLaser = loadImage("/res/pang/lasers/laserRed01.png");
        blueLaser = loadImage("/res/pang/lasers/laserBlue01.png");
        greenLaser = loadImage("/res/pang/lasers/laserGreen11.png");
        
        //Cargamos imagenes de meteores
        for (int i = 0; i < bigs.length; i++) {
            bigs[i] = loadImage("/res/pang/meteors/meteorGrey_big" + (i + 1) + ".png");

        }
        for (int i = 0; i < meds.length; i++) {
            meds[i] = loadImage("/res/pang/meteors/meteorGrey_big" + (i + 1) + ".png");
            meds[i] = loadImage("/res/pang/meteors/meteorGrey_med" + (i + 1) + ".png");
            smalls[i] = loadImage("/res/pang/meteors/meteorGrey_small" + (i + 1) + ".png");
            tinies[i] = loadImage("/res/pang/meteors/meteorGrey_tiny" + (i + 1) + ".png");

        }
//        Cargamos las imagenes de la explosiones
        for (int i = 0; i < ArrayFrameExp.length; i++) {
            ArrayFrameExp[i] = loadImage("/res/pang/explosion/" + i + ".png");
        }
        
        
        
//        Cargamos las imagenes de la Izq movimiento
        for (int i = 0; i < ArrayMovIzq.length; i++) {
            ArrayMovIzq[i] = loadImage("/res/pang/ships/playerI" + i + ".png");
        }
//        Cargamos las imagenes de la Derecha movimiento
        for (int i = 0; i < ArrayMovDer.length; i++) {
            ArrayMovDer[i] = loadImage("/res/pang/ships/playerD" + i + ".png");
        }
//    cargamos la fuente
        fontBig = loadFont("/res/pang/font/kenney_blocks.ttf", 42);

        fontMed = loadFont("/res/pang/font/kenney_blocks.ttf", 20);

//Imagenes de los ufos
        ufo = loadImage("/res/pang/ships/ufo.png");
//    Imagenes de los números.    
        for (int i = 0; i < numbers.length; i++) {
            numbers[i] = loadImage("/res/pang/numbers/" + i + ".png");
        }

        bk = loadImage("/res/pang/bk/bk.jpg");
         bk3 = loadImage("/res/pang/bk/bk3.jpg");

//    Imagenes de las vidas
        life = loadImage("/res/pang/others/life.png");

//        Inicializamos los sonidos
        backgroundMusic = loadSound("/res/pang/sounds/backgroundMusic.wav");
        explosion = loadSound("/res/pang/sounds/explosion.wav");
        playerLoose = loadSound("/res/pang/sounds/playerLoose.wav");
        playerShoot = loadSound("/res/pang/sounds/playerShoot.wav");
        ufoShoot = loadSound("/res/pang/sounds/ufoShoot.wav");
        passLevel = loadSound("/res/pang/sounds/passLevel.wav");

        greyBtn = loadImage("/res/pang/ui/grey_button.png");
        blueBtn = loadImage("/res/pang/ui/blue_button.png");
//   Cargado True por que  finaliza la carga.

        loaded = true;
    }
//    Cargamos las imagenes
//    Devolvemos la imagen y recibimos la ruta

    public static BufferedImage loadImage(String path) {
        count++;
        return Loader.loadImage(path);
    }
//Cargamos sonidos

    public static Clip loadSound(String path) {
        count++;
        return Loader.loadSound(path);
    }

//    Cargamos las fuentes
    public static Font loadFont(String path, int size) {
        count++;
        return Loader.loadFont(path, size);
    }
}
