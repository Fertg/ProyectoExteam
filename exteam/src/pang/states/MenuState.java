/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pang.states;

import exteam.Launcher;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import pang.gameObject.Constants;
import pang.graphics.Assets;
import pang.main.WindowPang;
import static pang.states.State.user;
import pang.ui.Action;
import pang.ui.Button;

/**
 *
 * @author Fernando
 */
public class MenuState extends State {

    private ArrayList<Button> buttons;

    WindowPang wp;

    public MenuState() {
        buttons = new ArrayList<Button>();
//Boton play
        buttons.add(new Button(
                Assets.greyBtn,
                Assets.blueBtn,
                Constants.WIDTH / 2 - Assets.greyBtn.getWidth() / 2,
                Constants.HEIGHT / 2 - Assets.greyBtn.getHeight(),
                Constants.PLAY,
                new Action() {
            @Override
            public void doAction() {
                State.changeState(new GameState());
            }
        }
        ));
//Boton Exit
        buttons.add(new Button(
                Assets.greyBtn,
                Assets.blueBtn,
                Constants.WIDTH / 2 - Assets.greyBtn.getWidth() / 2,
                Constants.HEIGHT / 2 + Assets.greyBtn.getHeight(),
                Constants.EXIT,
                new Action() {
            @Override
            public void doAction() {
//Thread.currentThread().interrupt();
                Launcher lu = new Launcher(user);
                lu.setVisible(true);
                wp.thread.getThreadGroup().interrupt();

            }
        }
        ));

    }
//Actualizamos los botones

    @Override
    public void update() {
        for (Button b : buttons) {
            b.update();
        }

    }
//Pintamos los botones del juego

    @Override
    public void draw(Graphics g) {
        g.drawImage(Assets.bk3, 0, 0, null);

        for (Button b : buttons) {
            b.draw(g);
        }
    }

}
