package pang.states;

import bbdd.ConectorBBDD;
import pang.gameObject.Chronometer;
import pang.gameObject.Constants;
import pang.gameObject.Message;
import pang.gameObject.MovingObject;
import pang.gameObject.Player;
import pang.gameObject.Size;
import pang.gameObject.meteor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import pang.graphics.Animation;
import pang.graphics.Assets;
import pang.graphics.Sound;
import java.awt.Color;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import pang.input.KeyBoard;
import pang.main.WindowPang;

import pang.math.Vector2D;
//Clase de estados del juego. extiende de Stado.

public class GameState extends State {

    private Player player;
    private ArrayList<MovingObject> movingObjects = new ArrayList<MovingObject>();
    private ArrayList<Animation> explosions = new ArrayList<Animation>();
    private Sound backgroundMusic;
    private Sound passLevel;
    private Chronometer crono;
    private int meteors;
//    Puntuacion del jugador
    private int score = 0;

//    Vida del Jugador
    private int lives = 3;
    WindowPang wp;
    private int level = 0;
//    Lista mensajes para poner en pantalla
    private ArrayList<Message> messages = new ArrayList<Message>();
    public boolean gameOver = false;
    public boolean pauseG = false;

    private Connection c;

//    Creamos el jugador, crono, meteoritos e inicializamos las oleadas de meteoros
    public GameState() {
        setConexion();
        player = new Player(new Vector2D(Constants.WIDTH / 2 - Assets.player.getWidth() / 2,
                Constants.HEIGHT - Assets.player.getHeight() - 25), new Vector2D(),
                Constants.PLAYER_MAX_VEL, Assets.player, this);

        crono = new Chronometer();
        movingObjects.add(player);
        crono.run(40);
        meteors = 1;
        startWave();
//        Musica de fondo
        backgroundMusic = new Sound(Assets.backgroundMusic);
        backgroundMusic.loop();

//        Ajustamos volumen
        backgroundMusic.changeVolume(-20.0f);

    }

    public void setConexion() {
        ConectorBBDD bbdd = new ConectorBBDD();
        c = bbdd.getConexion();
    }

//    Metodo para dividir meteoros.
    public void divideMeteor(meteor meteor) {

        Size size = meteor.getSize();

        BufferedImage[] textures = size.textures;

        Size newSize = null;

        switch (size) {
            case BIG:
                newSize = Size.MED;
                break;
            case MED:
                newSize = Size.SMALL;
                break;
            case SMALL:
                newSize = Size.TINY;
                break;
            default:
                return;
        }

        for (int i = 0; i < size.quantity; i++) {
            movingObjects.add(new meteor(
                    meteor.getPosition(),
                    new Vector2D(0, 1).setDirection(Math.random() * Math.PI * 2),
                    Constants.METEOR_VEL * Math.random() + 1,
                    textures[(int) (Math.random() * textures.length)],
                    this,
                    newSize
            ));
        }
    }

//    Metodo que inicia las Oleadas.
    private void startWave() {
        level++;
        messages.add(new Message(new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2), true, "NIVEL: : " + level, Color.yellow, true, Assets.fontBig, this));
        passLevel = new Sound(Assets.passLevel);
//        passLevel.changeVolume(-10.0f);
        double x, y;

        for (int i = 0; i < meteors; i++) {

            x = i % 2 == 0 ? Math.random() * Constants.WIDTH : 0;
            y = i % 2 == 0 ? 0 : Math.random() * Constants.HEIGHT;

            BufferedImage texture = Assets.bigs[(int) (Math.random() * Assets.bigs.length)];

            movingObjects.add(new meteor(
                    new Vector2D(x, y),
                    new Vector2D(0, 1).setDirection(Math.random() * Math.PI * 2),
                    Constants.METEOR_VEL * Math.random() + 1,
                    texture,
                    this,
                    Size.BIG
            ));

        }
        meteors++;

//        Creamos un nuevo meteoro
        if (crono.isRunning()) {
            crono.update();

        }

    }

//    Creamos la posicion de la animación
    public void playExplosion(Vector2D position) {
        explosions.add(new Animation(
                Assets.ArrayFrameExp,
                50,
                position.subtract(new Vector2D(Assets.ArrayFrameExp[0].getWidth() / 2, Assets.ArrayFrameExp[0].getHeight() / 2))
        ));
    }
    public boolean exit = false;

    public void update() {


        //PAUSA DEL JUEGO PARA REANUDAR
        
        if (KeyBoard.PAUSE || KeyBoard.EXIT) {
            for (int i = 0; i < 30; i++) {

            }
            if (!pauseG) {

                for (int i = 0; i < 30; i++) {
                    System.out.println(i);
                    messages.add(new Message(new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2), true, "PAUSE GAME", Color.BLACK, true, Assets.fontBig, this));
                    backgroundMusic.stop();
                    pauseG = true;
                }

            } else {
                for (int i = 0; i < 40; i++) {

                    backgroundMusic.play();
                    pauseG = false;
                }

            }

        }
        if (!pauseG) {
            for (int i = 0; i < movingObjects.size(); i++) {
                movingObjects.get(i).update();
            }

            for (int i = 0; i < explosions.size(); i++) {
                Animation anim = explosions.get(i);
                anim.update();
                if (!anim.isRunning()) {
                    explosions.remove(i);
                }

            }

            for (int i = 0; i < movingObjects.size(); i++) {
                if (movingObjects.get(i) instanceof meteor) {
                    return;
                }
            }

// Iniciamos la oleada
            startWave();
        }

    }
    
   

    @Override
    public void draw(Graphics g
    ) {
        Graphics2D g2d = (Graphics2D) g;
        g.drawImage(Assets.bk, 0, 0, null);
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);

        for (int i = 0; i < movingObjects.size(); i++) {
            movingObjects.get(i).draw(g);
        }
        for (int i = 0; i < messages.size(); i++) {
            messages.get(i).draw(g2d);
        }

        for (int i = 0; i < explosions.size(); i++) {
            Animation anim = explosions.get(i);
            g2d.drawImage(anim.getCurrentFrame(), (int) anim.getPosition().getX(), (int) anim.getPosition().getY(),
                    null);

        }
//  Pintamos los puntos
        drawScore(g);
// Pintamos las vidas
        drawLives(g);

    }

    public ArrayList<MovingObject> getMovingObjects() {
        return movingObjects;
    }
//Lo necesitamos para saber la posicion del jugador cuando dispare el ufo

    public Player getPlayer() {
        return player;
    }

    public void addScore(int value, Vector2D position) {
        score += value;

//Pintamos mensaje con puntuacion obtenida
        messages.add(new Message(position, true, "+ " + value + " Score - Level :" + level, Color.WHITE, false, Assets.fontMed, this));
    }

//    Metodo para dibujar la puntuacion 
    private void drawScore(Graphics g) {
//		Creamos el vector que nos idica donde dibujar la puntuación.
        Vector2D pos = new Vector2D(Constants.WIDTH - 200, 25);

//                Convertimos la puntuacion a string
        String scoreToString = Integer.toString(score);
//	Iteramos el string para pintar cada digito de la posicion			
        for (int i = 0; i < scoreToString.length(); i++) {

            g.drawImage(Assets.numbers[Integer.parseInt(scoreToString.substring(i, i + 1))],
                    (int) pos.getX(), (int) pos.getY(), null);
            pos.setX(pos.getX() + 20);

        }

    }

    private void drawLives(Graphics g) {
//		Vector posicion para colocarlo

        Vector2D livePosition = new Vector2D(25, 25);

        g.drawImage(Assets.life,
                (int) livePosition.getX(),
                (int) livePosition.getY(), null);

        g.drawImage(Assets.numbers[10],
                (int) livePosition.getX() + 40,
                (int) livePosition.getY() + 5, null);
//Pasamos las vidas a String
        String livesToString = Integer.toString(lives);
//		
        Vector2D pos = new Vector2D(livePosition.getX(), livePosition.getY());

        for (int i = 0; i < livesToString.length(); i++) {
//  Obtenemos el numero de vidas
            int number = Integer.parseInt(livesToString.substring(i, i + 1));
// Si el numero de vidas es menor que 0 la partida ha terminado

            if (lives == 0) {
                break;
            }
            g.drawImage(Assets.numbers[number],
                    (int) pos.getX() + 60, (int) pos.getY() + 5, null);

        }

    }

//    Resta una vida cuando eliminan al jugador
    public boolean subtractLife() {
        lives--;
        if (lives == 0) {
//            Cuando la vida es 0, mostramos el mensaje de Game Over en el centro de la pantalla
            messages.add(new Message(new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2), false, "GAME OVER  ", Color.BLACK, true, Assets.fontBig, this));
//        Creamos un timer el cual iniciamos en 6 segundos
            Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
//                    Reseteamos el estado del juego volviendo al menú
                    State.changeState(new MenuState());
                    try {
                        addScoreToBBDD();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            }, 6000);
//            Mensaje de restart , han pasado unos 2segundos aproximadamente, indica al jugador que se va a resetear.
            messages.add(new Message(new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT + 40 / 2), false, "Restart in 4 seconds  ", Color.BLACK, true, Assets.fontMed, this));
            gameOver = true;
        }
        return lives > 0;
    }
    
    public void musicOff(){
          backgroundMusic.stop();
    }
    
     public void vover() {

             Timer t = new Timer();
            t.schedule(new TimerTask() {
                @Override
                public void run() {
//                    Reseteamos el estado del juego volviendo al menú
                    State.changeState(new MenuState());
                    backgroundMusic.changeVolume(0);
                    try {
                        addScoreToBBDD();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                }
            },1000);
     
   
    }

//    Retornamos el array de mensajes
    public ArrayList<Message> getMessages() {
        return messages;
    }

    // Añade puntuación a la base de datos
    public void addScoreToBBDD() throws SQLException {
        int puntuacion = 0;
        String query = "SELECT game_credit FROM player WHERE username ='" + user + "';";
        Statement stmnt = c.createStatement();
        ResultSet rs = stmnt.executeQuery(query);
        while (rs.next()) {
            puntuacion = rs.getInt(1);
        }
        puntuacion += score;
        System.out.println(puntuacion);
        query = "UPDATE player SET game_credit = " + puntuacion + " WHERE username = '" + user + "';";
        stmnt.executeUpdate(query);
        c.close();
    }

}
