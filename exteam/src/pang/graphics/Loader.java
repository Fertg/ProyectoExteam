package pang.graphics;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

//Cargador de imagenes
public class Loader {

    // Clase que utiliza Java para guardar imagenes.
    public static BufferedImage loadImage(String path) {
        try {
            // Retornamos la imagen cargada.
            return ImageIO.read(Loader.class.getResource(path));
        } catch (Exception e) {
            // Error en la carga de la imagen.
            e.printStackTrace();
        }
        return null;
    }

//Cargamos la fuentes 
    public static Font loadFont(String path, int size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, Loader.class.getResourceAsStream(path)).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
//Cargamos los audios.
    public static Clip loadSound(String path) {
        try {
            Clip clip = AudioSystem.getClip();
            try {
                clip.open(AudioSystem.getAudioInputStream(Loader.class.getResource(path)));
            } catch (UnsupportedAudioFileException ex) {
                Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
            }
            return clip;
        } catch (LineUnavailableException | IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
