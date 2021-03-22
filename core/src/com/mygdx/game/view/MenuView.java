package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.battleships;

import java.awt.Button;

public class MenuView extends State{

    Texture background;
    Texture playBtn;
    Texture initButton;

    public MenuView(GameStateManager gsm) {
        super(gsm);

        background = new Texture("background.PNG");
        playBtn = new Texture("button_2.png");
        initButton = new Texture("initButton.png");

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

        sb.draw(playBtn,(battleships.WIDTH/2-100),battleships.HEIGHT-200,200 ,100);
        sb.draw(initButton,(battleships.WIDTH/2-100),100,200,100);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
