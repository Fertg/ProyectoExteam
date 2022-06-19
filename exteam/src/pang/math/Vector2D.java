package pang.math;
// Esta clase contiene todo lo que tenga que ver con cálculos
public class Vector2D {

    // Operaciones con vectores y cálculos de ángulos.
    private double x, y;

    // Inicializamos con coordenadas.
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    // Para inicializar un vector.(sobrecarga de métodos)
    public Vector2D() {
        x = 0;
        y = 0;
    }

    // Retorna la suma de dos vectores
    public Vector2D add(Vector2D v) {
        return new Vector2D(x + v.getX(), y + v.getY());
    }

    
    
    //resta de vectores con el merodo cabeza cola
    public Vector2D subtract(Vector2D v){
        return new Vector2D(x-v.getX(), y - v.getY());
    }
    

    
    
    // Multiplica un vector por un escalar. Nos permitirá modificar la magnitud del vector de dirección (Aceleración).
    public Vector2D scale(double value) {
        return new Vector2D(x * value, y * value);
    }
    // Control de dirección al traspasar límites de ventana.
    // Retorna el vector normalizado y limitado al valor recibido por parámetro.
    public Vector2D limit(double value) {
        if(getMagnitude() > value){
            return this.normalize().scale(value);
        }
        return this;
    }
    
    // Normalizamos el vector para poder frenar.
    public Vector2D normalize(){
        double magnitude =getMagnitude();
        return new Vector2D(x / magnitude, y / magnitude);
    }

    // Magnitud
    public double getMagnitude() {
        return Math.sqrt(x * x + y * y);
    }

    // Retorna un vector en base a un ángulo
    public Vector2D setDirection(double angle) {
        return new Vector2D(Math.cos(angle) * getMagnitude(), Math.sin(angle) * getMagnitude());
    }

//Obtiene X
    public double getX() {
        return x;
    }
//Posiciona X
    public void setX(double x) {
        this.x = x;
    }
//Obtiene Y
    public double getY() {
        return y;
    }
//Posiciona Y
    public void setY(double y) {
        this.y = y;
    }
    
    
//    Obtenemos el angulo de posicion del vector , del que invoca a este metodo. Basado en el triangulo de trigonometria
//    Seno opuesto = cateto partido por hipotenusa
    public double getAngle() {
		return Math.asin(y/getMagnitude());
	}
    
}
