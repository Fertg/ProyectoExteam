package pang.graphics;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 *
 * @author Fernando
 */

//Clase para cargar audios.
public class Sound {
    
//    Elemento clase clip
    private Clip clip;
    private FloatControl volume;

    public Sound(Clip clip) {
        this.clip = clip;
        
//        Control del volumen
        volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    }
//      Reproducce sonido
    public void play() {
//    Reproducimos desde el principio
        clip.setFramePosition(0);
//     Se reproduce
        clip.start();
    }

    public void stop() {
        clip.stop();
    }
//Musica de fondo en bucle
    public void loop() {
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    public int getFramePosition() {
        return clip.getFramePosition();
    }
//Control de volumen
    public void changeVolume(float value) {
        volume.setValue(value);
    }
}
