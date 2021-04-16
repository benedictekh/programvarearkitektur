package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.MakeBoardController;
import com.mygdx.game.model.Board;

import java.util.ArrayList;
import java.util.List;


public class MakeBoardView extends State implements Feedback{
    private Texture background;
    private MakeBoardController controller;
    private Board board;
    private int x_position;
    private int y_position;
    private ButtonView nextButton;
    private Boolean nextTouch = false;
    private ArrayList<List<Integer>> location;
    private Boolean bool = true;
    private BitmapFont font;


    protected MakeBoardView(GameStateManager gsm) {

        super(gsm);
        background = new Texture("background.PNG");
        //må endre fra player = null
        controller = new MakeBoardController( new Board(10, 10));
        board = new Board(10, 10);
        nextButton = new ButtonView("next.png",Battleships.WIDTH/2-100, Battleships.HEIGHT/2,200,75);
        font = new BitmapFont();


    }
    public void setNextTouch(boolean bool){
        this.nextTouch = bool;
    }
    public void setmarkedShip(ArrayList<List<Integer>> location){
        this.location = location;
    }

    public ArrayList<List<Integer>> getShipLocation(){
        return this.location;
    }
    public boolean getNextTouch(){
        return this.nextTouch;
    }


    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            x_position = Gdx.input.getX();
            y_position = Gdx.input.getY();
            System.out.println("Input position. " + x_position + ", " + y_position);
            controller.findShip(controller.getIndex(x_position,y_position));
            if(getNextTouch()){
                controller.moveShip(x_position,y_position,getShipLocation());
                setNextTouch(false);
            }
            if(controller.getMarkedShip() != null){

                System.out.println("marked ships position" + controller.getMarkedShip().getLocation());
                setmarkedShip(controller.getMarkedShip().getLocation());
                setNextTouch(true);
            }
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
        if(bool){
            font.getData().setScale(3,3);
            font.draw(sb, "great!", Battleships.WIDTH-300,Battleships.HEIGHT/2);

        }
        else if(!bool){
            font.getData().setScale(3,3);
            font.draw(sb, "not correct!", Battleships.WIDTH-300,Battleships.HEIGHT/2);
        }
        sb.end();
        drawBoardView();
        drawMarkedShip();

    }
    public void drawBoardView(){
        controller.drawBoardandShips();
    }
/*
    public void drawFeedbackGood(){
        controller.drawMarkedShip();
    }

    public void drawFeedbackBad() {
        controller.drawBoardandShips();
    }
 */
    public void setBoolean(boolean bool){
        this.bool = bool;
    }
    public Boolean getBoolean(){
        return this.bool;
    }

    @Override
    public void fireAction(boolean bool) {
        setBoolean(bool);
    }

    @Override
    public void dispose() {

    }
}
