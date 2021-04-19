package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.MakeBoardController;
import com.mygdx.game.model.Board;

import java.util.ArrayList;
import java.util.List;


public class MakeBoardView extends State{
    private Texture background;
    private MakeBoardController controller;
    private Board board;
    private int x_position;
    private int y_position;
    private ButtonView nextButton;
    private Boolean nextTouch = false;
    private ArrayList<List<Integer>> location;
    private ButtonView next;
    private Texture logo;
    private Texture setUpTutorial;
    private BitmapFont setUp;


    /**
     * the constructor, sets the background, MakeBoardController, board and "next-button"
     */
    protected MakeBoardView(GameStateManager gsm) {

        super(gsm);
        background = new Texture("background3.jpeg");
        //må endre fra player = null
        controller = new MakeBoardController( new Board(10, 10));
        board = new Board(10, 10);
        //nextButton = new ButtonView("next.png",Battleships.WIDTH/2-100, Battleships.HEIGHT/2,200,75);
        next = new ButtonView("next.png", Battleships.WIDTH/2+420, 90, 250, 95);
        logo = new Texture("logo.png");
        setUpTutorial = new Texture("BoardSetup.png");
        setUp = new BitmapFont();

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


    /**
     * moves ship to where the player places it on the board
     */
    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            x_position = Gdx.input.getX();
            y_position = Gdx.input.getY();
            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT-Gdx.input.getY(), 0);
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
            if(next.getRectangle().contains(touch.x, touch.y)){
                gsm.set(new TutorialView(gsm));
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

    /**
     * renders the MakeBoardView
     */
    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
        //sb.draw(logo, Battleships.WIDTH/2+250, Battleships.HEIGHT-350, 500, 500);
        sb.draw(setUpTutorial, Battleships.WIDTH/2+100, 230, 850, 780);
        sb.draw(next.getTexture(),next.Buttonx,next.Buttony,next.Width, next.Height);
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
