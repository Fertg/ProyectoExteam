package pang.main;

import exteam.Launcher;
import pang.gameObject.Constants;
import pang.graphics.Assets;
import pang.input.KeyBoard;
import pang.input.MouseInput;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import pang.states.GameState;
import pang.states.LoadingState;
import pang.states.State;
import static pang.states.State.user;

//Clase que pinta la ventana principal del juego implementa un Thread para iniciar los ciclos del juego.
public class WindowPang extends JFrame implements Runnable {

    // Variables para asignar el tamaño de la ventana
    public static final int WIDTH = 800, HEIGHT = 600;
    // Lienzo dónde dibujar todo
    private Canvas canvas;
    // Hilo de ejecución para el ciclo de vida del juego.
    public Thread thread;
    // Variable para saber cuando comienza y acaba la ejecución del juego
    private boolean running = false;
    // Variables necesarias para pintar elementos
    private BufferStrategy bs;
    private Graphics g;
    // Variables para configurar la velocidad del juego.
    // Asignamos 60 fotogramas por segundo
    private final int FPS = 60;
    // Tiempo requerido para aumentar cada fotograma. En nanosegundos.
    private double TARGETTIME = 1000000000 / FPS;
    // Almacena el tiempo que va pasando
    private double delta = 0;
    // Nos va a permitir saber a cuántos fotogramas está corriendo el juego. Comenzamos asignadole FPS.

    private GameState gameState;

    // 1. Añadimos atributo KeyBoard
    private KeyBoard keyBoard;
    private MouseInput mouseInput;

    // Valor para guardar el usuario
    String user;

    public WindowPang(String user) {       // Inicializamos la ventana
        this.user = user;
        setTitle("PANG EXTEAM");
        setSize(Constants.WIDTH, Constants.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);

        // Creamos el lienzo, dónde iremos dibujando todo.
        canvas = new Canvas();

        // 2. Inicializamos nuestro keyBoard
        keyBoard = new KeyBoard();
        mouseInput = new MouseInput();
        // Asignamos el tamaño
        canvas.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMaximumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMinimumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        // Permitimos que capture las teclas que se pulsen
        canvas.setFocusable(true);
        // Añadimos el lienzo a nuestra ventana
        add(canvas);

        // 3. Añadimos nuestro keyBoard al canvas
        canvas.addKeyListener(keyBoard);

//                Listener capturar click
        canvas.addMouseListener(mouseInput);
//           Listener capturar movimiendo     
        canvas.addMouseMotionListener(mouseInput);
        setVisible(true);

    }

    public WindowPang() {       // Inicializamos la ventana
        setTitle("PANG EXTEAM");
        setSize(Constants.WIDTH, Constants.HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setUndecorated(true);

        // Creamos el lienzo, dónde iremos dibujando todo.
        canvas = new Canvas();

        // 2. Inicializamos nuestro keyBoard
        keyBoard = new KeyBoard();
        mouseInput = new MouseInput();
        // Asignamos el tamaño
        canvas.setPreferredSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMaximumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        canvas.setMinimumSize(new Dimension(Constants.WIDTH, Constants.HEIGHT));
        // Permitimos que capture las teclas que se pulsen
        canvas.setFocusable(true);
        // Añadimos el lienzo a nuestra ventana
        add(canvas);

        // 3. Añadimos nuestro keyBoard al canvas
        canvas.addKeyListener(keyBoard);

//                Listener capturar click
        canvas.addMouseListener(mouseInput);
//           Listener capturar movimiendo     
        canvas.addMouseMotionListener(mouseInput);
        setVisible(true);

    }

    public static void main(String[] args) {
        new WindowPang().start();

    }

    // Actualizamos
    private void update() {

        // Actualizamos el valor de las teclas pulsadas, que controlan el movimiento de la nave.
        keyBoard.update();

        // 4. Actualizamos estado del juego.
//            gameState.update();
        State.getCurrentState().update();

        if (KeyBoard.EXIT) {
            int res = JOptionPane.showOptionDialog(new JFrame(), "¿Quieres salir del Juego?", "Salir",
                    JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    new Object[]{"Si", "No"}, JOptionPane.YES_OPTION);
            if (res == JOptionPane.YES_OPTION) {
                
                disposeX();
                try {
                    gameState.addScoreToBBDD();
                } catch (SQLException ex) {
                    Logger.getLogger(WindowPang.class.getName()).log(Level.SEVERE, null, ex);
                }

            } else if (res == JOptionPane.NO_OPTION) {
                System.out.println("Selected No!");
            }
        }

    }

    // Método que utilizaremos para pintar elementos
    private void draw() {

        // Obtenemos el bufer del canva
        bs = canvas.getBufferStrategy();
        // Inicialmente estará vacío, lo creamos.
        if (bs == null) {
            canvas.createBufferStrategy(3);
            return;
        }
        // Obtenemos del bufer el gráfico
        g = bs.getDrawGraphics();

        //------- Comenzamos a dibujar ------------
        // Dibujamos un rectánculo relleno de color negro que ocupe toda la pantalla
        g.setColor(Color.BLACK);

        g.fillRect(0, 0, Constants.WIDTH, Constants.HEIGHT);

        // 5. Dibujamos el objeto segun el estado de juego de este momento
        State.getCurrentState().draw(g);

        // Dibujamos los FPS actuales
        //------- Terminamos de dibujar ---------
        g.dispose();
        bs.show();
    }

    // Creamos método init, para inicializar los componentes que necesitemos. Se llamará solo una vez.
    private void init() throws UnsupportedAudioFileException {
//            Creamos hilo para cargar recursos
        Thread loadingThread = new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    Assets.init();
                } catch (UnsupportedAudioFileException ex) {
                    Logger.getLogger(WindowPang.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });

//        Cargamos el menu a través del hilo
        State.user = user;
        State.changeState(new LoadingState(loadingThread));
        //  Inicializamos el estado del juego.
//            gameState = new GameState();
    }

    // Métodos heredados de Runnable para la gestión del hilo
    @Override
    public void run() {
        // Hora actual
        long now = 0;
        // Hora anterior convertida a nanosegundos.
        long lastTime = System.nanoTime();
        // Controla los fotogramas que se van pintando
        int frames = 0;
        // Controla los frames que se pintan por segundo
        long time = 0;

        try {
            // Inicializamos los componentes.
            init();
        } catch (UnsupportedAudioFileException ex) {
            Logger.getLogger(WindowPang.class.getName()).log(Level.SEVERE, null, ex);
        }

        while (running) {
            // Obtenemos el tiempo actual
            now = System.nanoTime();
            // Obtenemos el tiempo que ha pasado.
            delta += (now - lastTime) / TARGETTIME;

            time += (now - lastTime);
            // Inicializamos ultimo tiempo con el actual
            lastTime = now;

            // Si ha pasado el tiempo, aumentamos un fotograma			
            if (delta >= 1) {
                update();
                draw();
                // Reiniciamos delta
                delta--;
                frames++;
            }

            // Si ya ha pasado un segundo, reiniciamos contador
            if (time >= 1000000000) {
                // Guardamos los frames que se han pintado en este segundo

                frames = 0;
                time = 0;

            }
        }

        // Llamamos al método stop que para la ejecución del hilo.
        stop();
    }

    public void disposeX() {
        Launcher lu = new Launcher(user);
        lu.setVisible(true);

        Thread.currentThread().destroy();
        this.dispose();

    }

    public void start() {

        thread = new Thread(this);
        thread.start();
        running = true;

    }

    public void stop() {
        try {
            thread.join();
            running = false;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
