package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.mygdx.game.battleships;

import java.awt.Button;
import java.awt.TextField;

public class MenuView extends State{

    Texture logo;
    Texture background;
    Texture playBtn;
    Texture initButton;

    public MenuView(GameStateManager gsm) {
        super(gsm);

        logo = new Texture("cover.png");
        background = new Texture("background1.jpg");
        playBtn = new Texture("playbtn1.png");
        initButton = new Texture("Settings.png");

    }



    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            //om den grønne knappen blir trykket på kommer man til board-et
            if (Gdx.input.getY()<= battleships.HEIGHT / 2) {
                gsm.set(new PlayView(gsm));
            }
        }
        if(Gdx.input.justTouched()){
            //om den blå knappen trykkes på kommer man til setting/tutorial + feedback
            if (Gdx.input.getY()> battleships.HEIGHT / 2) {
                gsm.set(new SettingsView(gsm));
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
        sb.draw(playBtn,(battleships.WIDTH/2-100),battleships.HEIGHT-200,200 ,75);
        sb.draw(initButton,(battleships.WIDTH/2-100),100,200,75);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
