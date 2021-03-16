package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.battleships;

public class MenuView extends State{

    Texture background;
    Texture playBtn;

    public MenuView(GameStateManager gsm) {
        super(gsm);

        background = new Texture("background.PNG");
        playBtn = new Texture("plybtn.PNG");

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayView(gsm));
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
        sb.draw(playBtn,(battleships.WIDTH/2-100),battleships.HEIGHT/2,200 ,200 );
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
