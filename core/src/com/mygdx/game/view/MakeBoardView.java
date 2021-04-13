package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.MakeBoardController;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Player;

public class MakeBoardView extends State{
    private Texture background;
    private MakeBoardController controller;
    private Board board;
    private int x_position;
    private int y_position;
    private ButtonView nextButton;
    private Boolean nextTouch = false;


    protected MakeBoardView(GameStateManager gsm) {

        super(gsm);
        background = new Texture("background.PNG");
        //må endre fra player = null
        controller = new MakeBoardController( new Player("hei", false));
        board = new Board(10, 10);
        nextButton = new ButtonView("next.png",Battleships.WIDTH/2-100, Battleships.HEIGHT/2,200,75);


    }
    public void setNextTouch(boolean bool){
        this.nextTouch = bool;
    }
    public boolean getNextThouch( ){
        return this.nextTouch;
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            x_position = Gdx.input.getX();
            y_position = Gdx.input.getY();
            System.out.println("Input position. " + x_position + ", " + y_position);
            controller.findShip(controller.getIndex(x_position,y_position));
            if(controller.getMarkedShip() != null){
                System.out.println("marked ships position" + controller.getMarkedShip().getLocation());
            }

            /*
            Vector3 touch = new Vector3(Gdx.input.getX(), battleships.HEIGHT-Gdx.input.getY(), 0);
            if(nextButton.getRectangle().contains(touch.x,touch.y)){
                gsm.set(new PlayView(gsm));
            }

             */

        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }
    public void drawMarkedShip() {
        if(controller.getMarkedShip() != null){
            controller.drawMarkedShip();
        }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
        //sb.draw(nextButton.getTexture(),nextButton.Buttonx,nextButton.Buttony,nextButton.Width ,nextButton.Height);
        sb.end();
        drawBoardView();
        drawMarkedShip();



    }
    public void drawBoardView(){
        controller.drawBoardandShips();

    }

    @Override
    public void dispose() {

    }
}
