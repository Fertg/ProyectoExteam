package duck_hunt.states;

import duck_hunt.gameObjects.Constants;
import duck_hunt.graphics.Assets;
import java.awt.Graphics;
import java.util.ArrayList;
import duck_hunt.ui.Action;
import duck_hunt.ui.Button;
import exteam.Launcher;
import static pang.states.State.user;

/**
 * Clase que define las propiedades y gestiona el menu de juego.
 *
 * @author elale
 */
public class MenuState extends State {

    // Botones del juego.
    private ArrayList<Button> buttons;

    // Constructor donde creamos los botones del juego.
    public MenuState() {
        buttons = new ArrayList<Button>();
        // Botón para comenzar el juego.
        buttons.add(new Button(
                Assets.greyBtn,
                Assets.blueBtn,
                Constants.PLAY,
                Constants.WIDTH / 2 - Assets.greyBtn.getWidth() / 2,
                Constants.HEIGHT / 2 - Assets.greyBtn.getHeight(),
                new Action() {
            @Override
            public void doAction() {
                changeState(new GameState());
            }
        }));

        // Botón para salir del juego.
        buttons.add(new Button(
                Assets.greyBtn,
                Assets.blueBtn,
                Constants.EXIT,
                Constants.WIDTH / 2 - Assets.greyBtn.getWidth() / 2,
                Constants.HEIGHT / 2 + Assets.greyBtn.getHeight() / 2,
                new Action() {
            @Override
            public void doAction() {
                 Launcher lu = new Launcher(user);
                lu.setVisible(true);
            }
        }
        ));
    }

    // Actualizamos los botones.
    @Override
    public void update() {
        for (Button button : buttons) {
            button.update();
        }
    }

    // Pintamos los botones.
    @Override
    public void draw(Graphics g) {
        g.drawImage(Assets.fondo, 0, 0,null);
        for (Button button : buttons) {
            button.draw(g);
        }
    }

}
