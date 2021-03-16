package com.mygdx.game.view;//package com.mygdx.game.view; //endret

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.battleships;
import com.mygdx.game.model.Board;
import com.mygdx.game.view.GameStateManager;
import com.mygdx.game.view.State; //endret

public class PlayView extends  State {

    private Texture background;
    private Texture board;

    public PlayView(GameStateManager gsm){
        super(gsm);

        background = new Texture("background.PNG");
        board = new Texture("board.PNG");


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
        sb.draw(background,0,0,battleships.WIDTH,battleships.HEIGHT);
        sb.draw(board,0,0,battleships.WIDTH,battleships.HEIGHT);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
