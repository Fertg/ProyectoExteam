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
//Clase cronometro, para controlar diferentes aspectos del juego por tiempo.
public class Chronometer {

    //inicio del cronometro y el tiempo actual para saber cuanto tiempo lleva corriendo
    private long delta, lastTime;
    //tiempo que dura el cronometro  
    private long time;
    //nos indica si el cronometro está en marcha
    private boolean running;
// Control de estado  y visible para saber cualndo volvemos a pintar la nave
    private boolean spawing, visible;
    
    public Chronometer() {
        delta = 0;
        lastTime = 0;
        running = false;
    }

    //arranca el cronometro.Necesitamos la variable tiempo para saberla duracion.
    public void run(long time) {
        running = true;
        this.time = time;
    }
    //este metodo actualiza el cronometro

    public void update() {
        //si el cronometro funciona, le sumamos tiempo a delta. que ha pasado desde la ultima vez entramos en este metodo, para saber cuando se cumple el tiempo del cronometro
        if (running) {
            delta += System.currentTimeMillis() - lastTime;

        }
        //si se cumple el tiepo de cronometro, lo paramos
        if (delta > time) {
            running = false;
            delta = 0;
        }
        //guardamos la hora actual
        lastTime = System.currentTimeMillis();
    }

    //Pasa saber si el cronometro está en marcha.
    public boolean isRunning(){
        return running;
    }
    
    
    
    
}
