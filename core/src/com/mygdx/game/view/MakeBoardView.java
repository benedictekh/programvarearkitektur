package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.MakeBoardController;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Player;

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
    private boolean bool = true;
    private boolean pressedOK = false;
    private BitmapFont font;
    private ButtonView wrongButton;
    private ButtonView rightButton;
    private Texture notValidMove;

    /**
     * QUALITY ATTRIBUTE: USABILITY
     *          The user can initilize the board by placing the ships where the user wants.
     *
     * This view presents the board for the player
     * The player can place the ships at preferred position.
     * The class has its own controller that handles the input actions.
     * The class implements the Feedback interface that talks with the MakeBoardController

     */


    protected MakeBoardView(GameStateManager gsm) {

        super(gsm);
        background = new Texture("background3.jpeg");
        controller = new MakeBoardController( new Player("hei", false));
        board = new Board(10, 10);
        nextButton = new ButtonView("next.png",Battleships.WIDTH/2-100, Battleships.HEIGHT/2,200,75);
        font = new BitmapFont();
        wrongButton = new ButtonView("wrong.png",Battleships.WIDTH/2+100, Battleships.HEIGHT-500,200,200);
        rightButton = new ButtonView("OK.png",Battleships.WIDTH/2+100, Battleships.HEIGHT-500,200,200);
        notValidMove = new Texture("notvalid.png");


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
     * Handle input is handling the input coordinates that is pressed.
     * The findShip function in the controller detects witch ship is pressed.
     * @param nextTouch is true if the player has pressed a ship and a ship is "marked".
     *                  The next thouch is then regesterd in the moveShip function.
     */

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

    /**
     * @param sb is what is drawn on.
     *
     * @param bool is used to give feedback to the user if they have a valid move.
     *
     */

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
        //sb.draw(nextButton.getTexture(),nextButton.Buttonx,nextButton.Buttony,nextButton.Width ,nextButton.Height);
        if(!bool){
            sb.draw(wrongButton.getTexture(),wrongButton.Buttonx,wrongButton.Buttony,wrongButton.Width ,wrongButton.Height);
            sb.draw(notValidMove,wrongButton.Buttonx+100,wrongButton.Buttony,300 ,150);
        }
        else if(bool){
            sb.draw(rightButton.getTexture(),rightButton.Buttonx,rightButton.Buttony,rightButton.Width ,rightButton.Height);
        }
        sb.end();
        drawBoardView();
        drawMarkedShip();

    }

    /**
     * draws the board and the ship.
     */

    public void drawBoardView(){
        controller.drawBoardandShips();
    }

    /**
     * if a ship is pressed, the ship is then marked my coloring the squares.
     */
    public void drawMarkedShip() {
        if(controller.getMarkedShip() != null){
            controller.drawMarkedShip();
        }
    }

    /**
     * OBSERVER PATTERN and QUALITY ATTRIBUTE: USEABILITY
     * here the observer pattern is used by detecting if the player is making an valid move and then giving the player a feedback.
     * The @param bool is declaring if the move is valid:true or notvalid:false.
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
