package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.battleships;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.ships.BattleShip;

import java.awt.Button;
import java.awt.Rectangle;

public class MenuView extends State{

    Texture logo;
    Texture background;

    private ButtonView playbutton;
    private ButtonView initButton;

    public MenuView(GameStateManager gsm) {
        super(gsm);

        logo = new Texture("cover.png");
        background = new Texture("background1.jpg");
        playbutton = new ButtonView("playbtn1.png",battleships.WIDTH/2-100, battleships.HEIGHT/2,200,75);
        initButton = new ButtonView("Settings.png",battleships.WIDTH/2-100, 100,200,75);


    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){

            Vector3 touch = new Vector3(Gdx.input.getX(), battleships.HEIGHT-Gdx.input.getY(), 0);

            if(playbutton.getRectangle().contains(touch.x,touch.y)){
                gsm.set(new InitializeGameView(gsm));
            }
            else if(initButton.getRectangle().contains(touch.x,touch.y)) {
                    gsm.set(new LoadingView(gsm));
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

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, battleships.WIDTH, battleships.HEIGHT);
        sb.draw(logo,battleships.WIDTH/2-275, battleships.HEIGHT-150, 500, 200);
        sb.draw(playbutton.getTexture(),playbutton.Buttonx,playbutton.Buttony,playbutton.Width ,playbutton.Height);
        sb.draw(initButton.getTexture(),initButton.Buttonx,initButton.Buttony,initButton.Width,initButton.Height);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
