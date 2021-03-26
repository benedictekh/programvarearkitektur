package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.battleships;

public class GameFinishedView extends State {

    Texture logo;
    Texture background;

    protected GameFinishedView(GameStateManager gsm) {
        super(gsm);
        logo = new Texture("cover.png");
        background = new Texture("background1.jpg");
    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        sb.draw(background, 0, 0, battleships.WIDTH, battleships.HEIGHT);
        sb.draw(logo,battleships.WIDTH/2-275, battleships.HEIGHT-150, 500, 200);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
