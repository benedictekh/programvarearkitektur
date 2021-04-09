package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.battleships;

public class MakeBoard extends State{
    private Texture background;
    protected MakeBoard(GameStateManager gsm) {
        super(gsm);
        background = new Texture("background1.jpg");


    }

    @Override
    protected void handleInput() {
        /*her legge hvis de blir trykket på */

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, battleships.WIDTH, battleships.HEIGHT);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
