package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.GameStateController;

public class MenuView extends State {

    private Texture logo;
    private Texture background;
    private ButtonView playbutton;
    private ButtonView initButton;

    /**
<<<<<<< HEAD
     * This is the first view and state that is added to the stack in the gsm.
     * It is the front page of the application.
     * There are two buttons witch sends the user to the game or to a tutorial of the game.
     */

    public MenuView(GameStateManager gsm) {
        super(gsm, new GameStateController());

        logo = new Texture("cover.png");
        background = new Texture("background1.jpg");
        playbutton = new ButtonView("playbutton.png", Battleships.WIDTH/2-200, Battleships.HEIGHT/2,400,125);
        initButton = new ButtonView("Settings.png", Battleships.WIDTH/2-150, 300,300,100);
    }

    /**
<<<<<<< HEAD
     * By pressing the playbutton the game is sent to the inlog site.
     * By pressing the intit button the user is sent to the ........
     */


    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){

            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT-Gdx.input.getY(), 0);

            if(playbutton.getRectangle().contains(touch.x,touch.y)){
                gsm.set(new InitializeGameView(gsm, new GameStateController()));
            }
            else if(initButton.getRectangle().contains(touch.x,touch.y)) {
                gsm.set(new GameFinishedView(gsm, gsc));
            }
            else{
                System.out.println("pressed outside");
            }
        }

    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    /**
     * renders the MenuView
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(logo, Battleships.WIDTH/2-750, Battleships.HEIGHT-500, 1500, 600);
        sb.draw(playbutton.getTexture(),playbutton.Buttonx,playbutton.Buttony,playbutton.Width ,playbutton.Height);
        sb.draw(initButton.getTexture(),initButton.Buttonx,initButton.Buttony,initButton.Width,initButton.Height);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
