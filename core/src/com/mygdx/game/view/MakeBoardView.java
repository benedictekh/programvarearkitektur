package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.battleships;
import com.mygdx.game.controller.BoardController;
import com.mygdx.game.controller.MakeBoardController;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Player;

public class MakeBoardView extends State{
    private Texture background;
    private MakeBoardController controller;
    private Board board;

    protected MakeBoardView(GameStateManager gsm) {

        super(gsm);
        background = new Texture("background.PNG");
        controller = new MakeBoardController( new Board(10, 10));
        board = new Board(10, 10);


    }

    @Override
    protected void handleInput() {
        /*her legge hvis de blir trykket på
        if(Gdx.input.justTouched()){
            x_position = Gdx.input.getX();
            y_position = Gdx.input.getY();

        }
        if (controller.isFinished()){
            gsm.set(new GameFinishedView(gsm));
        }

         */

    }

    @Override
    public void update(float dt) {
        //board.drawBoard();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, battleships.WIDTH, battleships.HEIGHT);
        drawBoardView();
        sb.end();


    }
    public void drawBoardView(){
        board.drawBoard();
    }

    @Override
    public void dispose() {

    }
}
