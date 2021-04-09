package com.mygdx.game.view;//package com.mygdx.game.view; //endret

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.battleships;
import com.mygdx.game.controller.BoardController;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Player;
import com.mygdx.game.view.GameStateManager;
import com.mygdx.game.view.State; //endret

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayView extends  State {

    private Texture background;
    private Texture board;
    private float x_position;
    private float y_position;
    private BoardController controller;
    private Player current;
    private BitmapFont font = new BitmapFont(); //or use alex answer to use custom font


    public PlayView(GameStateManager gsm){
        super(gsm);
        background = new Texture("background.PNG");
        controller = new BoardController( new Board(10, 10));
        current = controller.getPlayer();
    }


    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            x_position = Gdx.input.getX();
            y_position = Gdx.input.getY();
            System.out.println("Input position. " + x_position + ", " + y_position);
            controller.shoot(controller.getIndex(x_position, y_position));

        }
        if (controller.isFinished()){
            gsm.set(new GameFinishedView(gsm));
        }


        /*
        Her skal det være en funksjon som sender koordiandene som blir trykket på inn
        til controlleren. Controlleren vil videre regne ut hvilket rutenummer det er og sende dette
        Board model.
         */


    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();
        sb.draw(background,0,0,battleships.WIDTH,battleships.HEIGHT);
        //sb.draw(board,0,0,battleships.WIDTH,battleships.HEIGHT);
        font.draw(sb, current.getName(), battleships.WIDTH - 50, battleships.HEIGHT -10);
        sb.end();
        drawBoard(current);
        current = controller.getPlayer();



    }

    public void drawBoard(Player player){
        player.getBoard().drawBoard();
        player.getBoard().drawShips();
        player.getBoard().drawUpdatedBoard();
    }

    @Override
    public void dispose() {

    }


}
