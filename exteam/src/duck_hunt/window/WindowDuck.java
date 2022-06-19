package duck_hunt.window;

import duck_hunt.gameObjects.Constants;
import duck_hunt.graphics.Assets;
import duck_hunt.input.KeyBoard;
import duck_hunt.input.MouseInput;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import duck_hunt.states.GameState;
import duck_hunt.states.LoadingState;
import duck_hunt.states.State;
import exteam.Launcher;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author elale
 */
public class WindowDuck extends JFrame implements Runnable {

    //Objeto canvas sobre el que dibujaremos todo
    private Canvas canvas;
    //Creamos el hilo de ejecución del ciclo de vida del juego.
    private Thread thread;
    //Variaible para saber cuando comienza y acaba la ejecución del juego
    private boolean running = false;
    //Variables necesarias para pintar los elementos
    private BufferStrategy bs;
    private Graphics g;
    // Variables para configurar la velocidad del juego
    //Asignamos 60 fps
    private final int FPS = 60;
    //Tiempo requerido para pintar un frame, en nanosegundos
    private final double TARGETTIME = 1000000000 / FPS;
    //Almacena el tiempo que va pasando
    private double delta = 0;
    //Nos va a permitir saber a cuandos FPS corre el juego
    private int averageFps = FPS;

    //Objeto para controlar el estado del juego
    private GameState gameState;

    // Creo un objeto de tipo keyBoard.
    private KeyBoard keyBoard;
    // Creo un objeto de tipo MouseInput.
    private MouseInput mouseInput;

    // Valor para guardar el usuario
    String user;

    // Constructor parametrizado de la clase
    public WindowDuck(String user) {

        // Inicializamos el nombre de usuario
        this.user = user;

        //Como extendemos de JFrame, podemos utilizar sus métodos
        setTitle("Duck Hunt - ExTeam");
        setSize(Constants.WIDTH, Constants.HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);

        //Dibujaremos el canvas(lienzo)
        canvas = new Canvas();
        // Inicializamos el keyboard.
        keyBoard = new KeyBoard();
        mouseInput = new MouseInput();
        //Indicamos el tamaño
        canvas.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMaximumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMinimumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        //Permiitimos que capture las telcas que se pulsen
        canvas.setFocusable(true);
        // Añadimos el lienzo a nuestra ventana.
        add(canvas);
        canvas.addKeyListener(keyBoard);
        // Añadimos el listener del raton para capturar los botones presionados.
        // Añadimos el listener del raton para capturar los botones presionados.
        canvas.addMouseListener(mouseInput);
        // Añadimos el listener para capturar el movimiento del raton.
        canvas.addMouseMotionListener(mouseInput);
        setVisible(true);

    }

    //Constructor por defecto de la clase
    public WindowDuck() {
        //Como extendemos de JFrame, podemos utilizar sus métodos
        setTitle("Duck Hunt - ExTeam");
        setSize(Constants.WIDTH, Constants.HEIGHT);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);

        //Dibujaremos el canvas(lienzo)
        canvas = new Canvas();
        // Inicializamos el keyboard.
        keyBoard = new KeyBoard();
        mouseInput = new MouseInput();
        //Indicamos el tamaño
        canvas.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMaximumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMinimumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        //Permiitimos que capture las telcas que se pulsen
        canvas.setFocusable(true);
        // Añadimos el lienzo a nuestra ventana.
        add(canvas);
        canvas.addKeyListener(keyBoard);
        // Añadimos el listener del raton para capturar los botones presionados.
        // Añadimos el listener del raton para capturar los botones presionados.
        canvas.addMouseListener(mouseInput);
        // Añadimos el listener para capturar el movimiento del raton.
        canvas.addMouseMotionListener(mouseInput);
        setVisible(true);

    }
    
   
    //Método principal
    public static void main(String[] args) {
        //Creamos el hilo principal e inicializamos el hilo prontipal
        new WindowDuck().start();
    }

    // Inicializa los componentes del juego
    private void init() {
        // Creamos hilo para cargar recursos.
        Thread loadingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Assets.init();
            }
        });
        // Pasamos el nombre de usuario al estado del juego y lo inicializamos
        State.user = user;
        State.changeState(new LoadingState(loadingThread));
    }
    public void disposeX() {
        Launcher lu = new Launcher(user);
        lu.setVisible(true);

        Thread.currentThread().destroy();
        this.dispose();

    }
    //Actualizamos la información de los elementos del juego
    private void update() {
        
        if (KeyBoard.EXIT) {
            int res = JOptionPane.showOptionDialog(new JFrame(), "¿Quieres salir del Juego?", "Salir",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si", "No"}, JOptionPane.YES_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                disposeX();       
                try {
                    gameState.addScoreToBBDD();
                } catch (SQLException ex) {
                    Logger.getLogger(WindowDuck.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (res == JOptionPane.NO_OPTION) {
                System.out.println("Selected No!");
            }
        }
        
        
        keyBoard.update();
        // Actualizamos el estado del juego
        State.getCurrentState().update();
    }

    //Pintamos los objetos sobre el lienzo
    private void draw() throws InterruptedException {
        //Obtenemos el buffer del canvas (sobre que parte de la pantalla queremos pintar)
        bs = canvas.getBufferStrategy();
        //Inicialmente estará vacio, lo creamos
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        //Obtenemos el elemento Graphics del bs
        g = bs.getDrawGraphics();
        // ---------- Comenzamos a dibujar ----------
        //Limpiamos el lienzo
//        g.clearRect(0, 0, Constants.WIDTH, Constants.HEIGHT);
        //Pintamos el fondo de negro
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        //Lamamos al metodo draw de gameState
        State.getCurrentState().draw(g);

        //Dibujamos al jugador
        // ---------- Terminamos de dibujar ----------
        g.dispose();
        bs.show();
    }

    //Método abstracto implementado de Runnable
    @Override
    public void run() {

        //Registro que almacena el tiempo que va pasando
        long now = 0;
        //Registro que almacena el tiempo actual del sistema en nanosegundos
        long lastTime = System.nanoTime();
        //Controlar los fotogramas que se van pintando
        int frames = 0;
        //Controlar los segundos que van pasando para sacar la media de FPS
        long time = 0;

        //Inicializamos los componentes
        init();

        //Dentro de este bucle se pintarán los objetos y se actualizarán
        //Mientras que el juego esté corriendo
        while (running) {
            //Obtenemos el tiempo actual de la iteración.
            now = System.nanoTime();
            //Incrementamos delta con el tiempo que ha pasado hasta ahora
            //Lo dividimos por targetTime que es nuestro tiempo objetivo para saber cuando tenemos que pintar el frame
            delta += (now - lastTime) / TARGETTIME;
            //Almacenamos el tiempo que va pasando
            time += (now - lastTime);
            //Actualizamos el lastTime para que este listo en proximas iteraciones
            lastTime = now;

            //Cuando haya pasado el tiempo definido, pinto
            if (delta >= 1) {
                //Actualizamos
                update();
                try {
                    //Pintamos
                    draw();
                } catch (InterruptedException ex) {
                    Logger.getLogger(WindowDuck.class.getName()).log(Level.SEVERE, null, ex);
                }
                //Restamos 1 a delta para reiniciarlo. No se iguala a 0 para tener en cuenta posibles picos
                delta--;
                //Aumentamos los frames pintados
                frames++;

                //Nos muestra por consola cada frame pintado
                //System.out.println(frames);
            }
            //Si ya ha pasado un segundo, reiniciamos contador
            if (time >= 1000000000) {
                //Guardamos los frames que se han pintado
                averageFps = frames;
                //Reiniciamos tiempo y frames
                time = 0;
                frames = 0;
            }

        }
        //Finalizamos la ejecución del hilo
        stop();
    }

    //Método que crea el hilo principal del juego
    public void start() {
        //Creamos el hilo
        thread = new Thread(this);
        //Inicializamos el hilo
        thread.start();
        //Cambiamos la bandera de estado a true
        running = true;
    }

    //Método que finaliza la ejecución del hilo principal del juego
    private void stop() {
        try {
            //Paramos la ejecución del hilo
            thread.join();
            //Cambiamos la bandera de estado a false
            running = false;
        } catch (InterruptedException ex) {
            Logger.getLogger(WindowDuck.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
