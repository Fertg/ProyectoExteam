/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package duck_hunt.states;

import bbdd.ConectorBBDD;
import duck_hunt.gameObjects.Chronometer;
import duck_hunt.gameObjects.Constants;
import duck_hunt.gameObjects.Crosshair;
import duck_hunt.gameObjects.Enemy;
import duck_hunt.gameObjects.Message;
import duck_hunt.gameObjects.MovingObject;
import duck_hunt.graphics.Animation;
import duck_hunt.graphics.Assets;
import duck_hunt.graphics.Sound;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import duck_hunt.math.Vector2D;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import pang.input.KeyBoard;

/**
 *
 * @author elale Clase para gestionar el estado principal del juego
 */
public class GameState extends State {

    //private Player player;
    private Crosshair crosshair;
    private ArrayList<MovingObject> movingObjects = new ArrayList<>();
    private ArrayList<Animation> explosions = new ArrayList<Animation>();
    // Array de Mensajes para pintar en pantalla.
    private ArrayList<Message> messages = new ArrayList<Message>();
    // Puntuación.
    private int score = 0;
    // Vidas
    private int lives = Constants.LIVES;
    // Musica de fondo.
    private Sound backgroundMusic;
    // Musica de nivel
    private Sound levelUp;
    // Oleadas
    private int waves = 0;
    // Control de fin de juego
    private Chronometer gameOverTimer;
    private Chronometer waveTimer;
    private boolean gameOver;
    private Message gameOverMsg;
    private BufferedImage background;
    private BufferedImage layout;
    private BufferedImage agua, agua2;
    private Sound playerLoose, game_over;
    public boolean pauseG = false;

    // Atributo conexión para BBDD
    private Connection c;
    private Timer t;

    // Constructor de la clase
    public GameState() {

//                waveTimer.run(10000);
        
        gameOver = false;
        crosshair = new Crosshair(new Vector2D(Constants.WIDTH / 2 - Assets.player.getWidth() / 2, Constants.HEIGHT / 2 - Assets.player.getHeight() / 2), new Vector2D(), 0, Assets.player, this);
        //player = new Player(new Vector2D(Constants.WIDTH / 2 - Assets.player.getWidth() / 2, Constants.HEIGHT - 150), new Vector2D(0, 0), Constants.PLAYER_MAX_VEL, Assets.player, this);
        //movingObjects.add(player);
        movingObjects.add(crosshair);
        levelUp = new Sound(Assets.levelUp);
        backgroundMusic = new Sound(Assets.backgroundMusic);
        backgroundMusic.loop();
        backgroundMusic.changeVolume(-10.0f);
        gameOverTimer = new Chronometer();
        waveTimer = new Chronometer();
        playerLoose = new Sound(Assets.playerLoose);
        game_over = new Sound(Assets.game_over);
        game_over.changeVolume(-10.0f);
        background = Assets.background0;
        agua = Assets.agua;
        agua2 = Assets.agua2;
        layout = Assets.caseta;
        startWave();
    }

    // Método para inicializar la conexión con la base de datos
    public void setConexion() {
        ConectorBBDD bbdd = new ConectorBBDD();
        c = bbdd.getConexion();
    }

    // Añadimos el valor pasado por parametro a la puntuación.
    public void addScore(int value, Vector2D position) {
        score += value;
        // Pintamos mensaje con puntuación obtenida por eliminar el objeto.
        messages.add(new Message(position,
                true,
                "+" + value + " score",
                Color.WHITE,
                false,
                Assets.fontMed,
                this));
    }

    public ArrayList<MovingObject> getMovingObjects() {
        return movingObjects;
    }

    // Añadimos animación al array de animaciones.
    // Recibe la posición del objeto que ha colisionado.
    public void playExplosion(Vector2D position) {
        // Añadimos una animación al array de explosiones, con la velocidad y el centro de la explosión.
        explosions.add(new Animation(Assets.arrayFramesExp, Constants.ANIMATION_VEL, position.subtract(
                new Vector2D(Assets.arrayFramesExp[0].getWidth() / 2, Assets.arrayFramesExp[0].getHeight() / 2))));
    }

    private void startWave() {

        waves++;
        if (waves != 1) {

            t.schedule(new TimerTask() {
                @Override
                public void run() {

                    backgroundMusic.stop();
                    System.out.println(t.purge());
                    gameOver();

                }
            }, 10000);
            levelUp.play();
        }
        // Mostramos mensaje de nuevo nivel.
        messages.add(new Message(new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2),
                true,
                "LEVEL " + waves,
                Color.WHITE,
                true,
                Assets.fontBig, this));
        messages.add(new Message(new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 3),
                true,
                "10 SEGUNDOS RESTANTES",
                Color.WHITE,
                true,
                Assets.fontBig, this));

        for (int i = 0; i < 6; i++) {
            BufferedImage texture = Assets.enemies[(int) (Math.random() * Assets.enemies.length)];
            Enemy e = new Enemy(new Vector2D(i * -200, Constants.HEIGHT / 4 * 2),
                    new Vector2D(5, 0),
                    0,
                    texture,
                    this);
            movingObjects.add(e);

        }
        for (int i = 0; i < 8; i++) {
            BufferedImage texture = Assets.enemies[(int) (Math.random() * Assets.enemies.length)];
            Enemy e = new Enemy(new Vector2D(i * -150, Constants.HEIGHT / 4),
                    new Vector2D(5, 0),
                    0,
                    texture,
                    this);
            movingObjects.add(e);
        }

//        waveTimer.update();
    }

    // Dibujamos la puntuación.
    private void drawScore(Graphics g) {
        // Creamos el vector que nos indi1ca dónde dibujar la puntuación.
        Vector2D pos = new Vector2D(Constants.WIDTH - 100, 25);
        // Convertimos la puntuación a String.
        String scoreToString = Integer.toString(score);
        // Iteramos el string para pintar cada digito de la puntuacion.
        for (int i = 0; i < scoreToString.length(); i++) {
            // Pintamos la imagen correspondiente del array de numeros que corresponde con el dígito de la puntuación.
            g.drawImage(Assets.numbers[Integer.parseInt(scoreToString.substring(i, i + 1))], (int) pos.getX(), (int) pos.getY(), null);
            pos.setX(pos.getX() + 20);
        }
    }

    // Dibujamos las vidas.
    private void drawLives(Graphics g) {
        Vector2D livePosition = new Vector2D(25, 25);
        g.drawImage(Assets.live, (int) livePosition.getX(), (int) livePosition.getY(), null);
        g.drawImage(Assets.numbers[10], (int) livePosition.getX() + 40, (int) livePosition.getY() + 5, null);
        String livesToString = Integer.toString(lives);
        // Obtenemos el vector de posición para pintar el número de vidas.
        Vector2D pos = new Vector2D(livePosition.getX(), livePosition.getY());
        // Convertimos la puntuación a string   .     
        for (int i = 0; i < livesToString.length(); i++) {
            // Pintamos la imagen correspondiente del array de numeros que corresponde con el dígito de la puntuación.
            // Si el numero de vidas es menor que 0, la partida habrá terminado.
            if (lives <= 0) {
                break;
            }
            g.drawImage(Assets.numbers[Integer.parseInt(livesToString.substring(i, i + 1))],
                    (int) pos.getX() + 60, (int) pos.getY() + 5, null);
            livePosition.setX(pos.getX() + 20);
        }
    }

    // Restamos una vida
    public void substractLife() {
        lives--;
        playerLoose.play();
        if (lives <= 0) {
            gameOver();
        }
    }

    // Retornamos el array de mensajes
    public ArrayList<Message> getMessages() {
        return messages;
    }

    public void pause(boolean pause) {
        messages.add(new Message(new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2), true, "PAUSE GAME", Color.yellow, true, Assets.fontBig, this));

        if (!pause) {
            pauseG = true;
        } else {
            pauseG = false;
        }

    }

    // Actualiza
    @Override
    public void update() {
        if (KeyBoard.PAUSE  || KeyBoard.EXIT) {
            for (int i = 0; i < 30; i++) {

            }
            if (!pauseG) {

                for (int i = 0; i < 30; i++) {

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
            waveTimer.update();
            gameOverTimer.update();
            if (gameOver && !gameOverTimer.isRunning()) {

                State.changeState(new MenuState());
            }
            crosshair.update();

            // Buscamos el tiro en el pato
            if (crosshair.isTrigger()) {
                for (int i = 0; i < movingObjects.size(); i++) {
                    // Si tenemos un enemigo
                    if (movingObjects.get(i) instanceof Enemy) {
                        // Si las coordenadas de ese enemigo contienen el centro de la reticula, eliminamos enemigo
                        if (movingObjects.get(i).getPosition().getX() + Assets.player.getWidth() / 2 < crosshair.getCenter().getX()
                                && movingObjects.get(i).getPosition().getX() + Assets.duck_outline_brown.getWidth() >= crosshair.getPosition().getX()
                                && movingObjects.get(i).getPosition().getY() + Assets.player.getHeight() / 2 < crosshair.getCenter().getY()
                                && movingObjects.get(i).getPosition().getY() + Assets.duck_outline_brown.getHeight() >= crosshair.getPosition().getY()) {
                            movingObjects.get(i).destroy();
                        }
                    }
                }
            }
            ///

            //Pintamos los elementos incluidos en el array
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
//            if (waveTimer.isRunning()) {
//                gameOver();
//            }
            // Iteramos la lista de objetos movibles en busca de enemigos.
            for (int i = 0; i < movingObjects.size(); i++) {
                // Si hay enemigos, retornamos, no iniciamos oleada.
                if (movingObjects.get(i) instanceof Enemy) {
                    return;
                }
            }
            if (waves != 1) {
                t.cancel();
            }
            t = new Timer();

            // Iniciamos oleada.
            startWave();
        }
    }

    // Dibujamos el objeto Graphics
    public void draw(Graphics g) {
        // Renderizamos la imagen (Solución al problema de perdida de calidad al rotar (antialiasing))
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        if (gameOver) {
            try {
                gameOverMsg.draw(g2d);
            } catch (Exception e) {
            }
            return;
        }
        g2d.drawImage(background, null, null);
        for (int i = 0; i < movingObjects.size(); i++) {
            if (i == 0) {
                g2d.drawImage(agua2, null, null);
            }
            movingObjects.get(i).draw(g2d);
        }
        for (int i = 0; i < explosions.size(); i++) {
            Animation anim = explosions.get(i);
            g2d.drawImage(anim.getCurrentFrame(), (int) anim.getPosition().getX(), (int) anim.getPosition().getY(), null);
        }
        for (int i = 0; i < messages.size(); i++) {
            messages.get(i).draw(g2d);
        }
        g2d.drawImage(agua, null, null);
        g2d.drawImage(layout, null, null);
        crosshair.draw(g);
        drawScore(g);
//        drawLives(g);
    }

    private void gameOver() {
        backgroundMusic.stop();
        game_over.play();
        pauseG = true;
        gameOverMsg = new Message(
                new Vector2D(Constants.WIDTH / 2, Constants.HEIGHT / 2),
                true,
                "GAME OVER",
                Color.WHITE,
                true,
                Assets.fontBig,
                this);

        gameOver = true;
        try {
            addScoreToBBDD();
        } catch (SQLException ex) {
            Logger.getLogger(GameState.class.getName()).log(Level.SEVERE, null, ex);
        }
        State.changeState(new MenuState());
    }

    // Añade puntuación a la base de datos
    public void addScoreToBBDD() throws SQLException {
        setConexion();
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
        System.out.println(query);
        stmnt.executeUpdate(query);
        c.close();
    }

}
