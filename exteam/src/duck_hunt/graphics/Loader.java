/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duck_hunt.graphics;

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

/**
 *
 * @author elale Clase que utilizaremos para cargar elementos; imagenes, sonidos, tipografías...
 */
public class Loader {

    // BufferedImage es la clase utilizada por Java para guardar imágenes
    public static BufferedImage imageLoader(String path) {
        try {
            return ImageIO.read(Loader.class.getResource(path));
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    // Font es la clase utilizada por Java para guardar fuentes.
    public static Font loadFont(String path, int size) {
        try {
            return Font.createFont(Font.TRUETYPE_FONT, Loader.class.getResourceAsStream(path)).deriveFont(Font.PLAIN, size);
        } catch (FontFormatException | IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    // Clip es la clase utilizada por Java para guardar sonidos.
    public static Clip loadSound(String path) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(Loader.class.getResource(path)));
            return clip;
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}
