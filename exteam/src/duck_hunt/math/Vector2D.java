/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duck_hunt.math;

/**
 *
 * @author elale Clase para realizar los cálculos con vectores necesarios para los atributos de posición, velocidad, etc.
 */
public class Vector2D {

    // Coordenadas x e y del vector
    public double x, y;

    //Inicializamos el vector con las coordenadas recibidas por parámetro
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vector2D() {
        x = 0;
        y = 0;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    // Obtenemos la magnitud del vector
    public double getMagnitude() {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    // Control de direccion al traspasar los limites de la ventana
    // Retorna el vector normalizado y limitado al valor recibido por parámetro
    public Vector2D limit(double value) {
        // Siempre que la magnitud del vectir sea mayor que el limite de velocidad retornamos el 
        // vector normalizado y limitado al valor recibido
        if (getMagnitude() > value) {
            return this.normalize().scale(value);
        }
        return this;
    }

    // Retorna el vector director en base al ángulo que recibe
    public Vector2D setDirection(double angle) {
        return new Vector2D(Math.cos(angle) * getMagnitude(), Math.sin(angle) * getMagnitude());
    }

    // Retorna un vector que es la suma de los dos vectores.
    public Vector2D add(Vector2D v) {
        return new Vector2D(x + v.getX(), y + v.getY());
    }

     // Resta de vectores con el método cabeza-cola
    public Vector2D subtract(Vector2D v) {
        return new Vector2D(x - v.getX(), y - v.getY());
    }
    
    // Realiza una multiplicación escalar.
    // Nos permite modificar la magnitud del vector de dirección (aceleración)
    public Vector2D scale(double value) {
        return new Vector2D(x * value, y * value);
    }

    // Retornamos el vector normalizado para obtener la desaceleración
    public Vector2D normalize() {
        double magnitude = getMagnitude();
        return new Vector2D(x / magnitude, y / magnitude);
    }
}
