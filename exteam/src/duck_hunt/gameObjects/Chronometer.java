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
public class Chronometer {

    // Inicio del cronómetro y el tiempo actual para saber cuanto tiempo lleva corriendo
    private long delta, lastTime;
    // El tiempo que dura el cronómetro
    private long time;
    // Bandera que indica si el cronómetro está en marcha
    private boolean running;

    public Chronometer() {
        delta = 0;
        lastTime = 0;
        running = false;
    }

    // Arranca el cronómetro. Neceistamos la variable tiempo para saber la duración
    public void run(long time) {
        running = true;
        this.time = time;
    }

    public void update() {
        // Si el chrono está en marcha acumulamos en delta el tiempo que ha pasado desde la
        // última vez que entramos a este método para saber cuando se cumple el tiempo.
        if (running) {
            delta += System.currentTimeMillis() - lastTime;
        }

        // Si se cumple el tiempo del chrono, lo paramos
        if (delta >= time) {
            running = false;
            delta = 0;
        }
        
        // Guardamos la hora actual
        lastTime = System.currentTimeMillis();
    }
    
    // Para consultar la bandera y saber si está en marcha.
    public boolean isRunning(){
        return running;
    }
    
    public long devuelveTime(){
        return delta;
    }

}
