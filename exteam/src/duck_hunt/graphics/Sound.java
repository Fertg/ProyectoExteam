package duck_hunt.graphics;

import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

/**
 * Clase que define las propiedades de un objeto sonido.
 *
 * @author elale
 */
public class Sound {

    // Clip que guardaremos.
    private Clip clip;
    // Volumen del clip.
    private FloatControl volume;

    // Constructor de la clase
    public Sound(Clip clip) {
        this.clip = clip;
        volume = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
    }

    // Constructor por defecto.
    public Sound() {
    }

    // Método que nos permitirá reproducir sonidos en bucle.
    public void loop() {
        clip.setFramePosition(0);
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    // Método que nos permitirá reproducir un sonido desde el frame 0 (desde el inicio).
    public void play() {
        clip.setFramePosition(0);
        clip.start();
    }

    // Método que nos permitirá parar un sonido.
    public void stop() {
        clip.stop();
    }

    // Getter del frame actual del sonido.
    public int getFramePosition() {
        return clip.getFramePosition();
    }

    // Setter del volumen del sonido.
    public void changeVolume(float value) {
        volume.setValue(value);
    }

}
