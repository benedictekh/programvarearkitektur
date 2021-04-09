package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.battleships;
import com.mygdx.game.controller.BoardController;
import com.mygdx.game.controller.MakeBoardController;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Player;

public class MakeBoardView extends State{
    private Texture background;
    private MakeBoardController controller;
    private Board board;
    private float x_position;
    private float y_position;
    private ButtonView nextButton;


    protected MakeBoardView(GameStateManager gsm) {

        super(gsm);
        background = new Texture("background.PNG");
        //controller = new MakeBoardController( new Board(10, 10));
        board = new Board(10, 10);
        nextButton = new ButtonView("next.png",battleships.WIDTH/2-100, battleships.HEIGHT/2,200,75);


    }

    @Override
    protected void handleInput() {
        /*her legge hvis de blir trykket på
         */

        if(Gdx.input.justTouched()){
            x_position = Gdx.input.getX();
            y_position = Gdx.input.getY();
            System.out.println("Input position. " + x_position + ", " + y_position);
            Vector3 touch = new Vector3(Gdx.input.getX(), battleships.HEIGHT-Gdx.input.getY(), 0);
            if(nextButton.getRectangle().contains(touch.x,touch.y)){
                gsm.set(new PlayView(gsm));
            }
            //if ships blir thouched - >

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
        sb.draw(nextButton.getTexture(),nextButton.Buttonx,nextButton.Buttony,nextButton.Width ,nextButton.Height);
        sb.end();
        drawBoardView();



    }
    public void drawBoardView(){
        board.drawBoard();
        board.drawShips();
    }

    @Override
    public void dispose() {

    }
}
