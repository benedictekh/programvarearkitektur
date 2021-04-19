package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Battleships;
import com.mygdx.game.GameIdHolder;

public class GameFinishedView extends State {

    Texture logo;
    Texture background;

    protected GameFinishedView(GameStateManager gsm) {
        super(gsm);
        logo = new Texture("cover.png");
        background = new Texture("background1.jpg");
        GameIdHolder g= GameIdHolder.getInstance();
        String a = g.gameId;
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

        sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(logo, Battleships.WIDTH/2-275, Battleships.HEIGHT-150, 500, 200);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
