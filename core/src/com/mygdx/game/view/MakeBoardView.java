package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.LoadingController;
import com.mygdx.game.controller.MakeBoardController;
import com.mygdx.game.controller.PlayController;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Player;

import java.util.ArrayList;
import java.util.List;


public class MakeBoardView extends State implements Feedback{
    private Texture background;
    private MakeBoardController controller;
    //private Board board;
    private int x_position;
    private int y_position;
    private ButtonView nextButton;
    private Boolean nextTouch = false;
    private ArrayList<List<Integer>> location;
    private boolean bool = true;
    private boolean pressedOK = false;
    private BitmapFont font;
    private ButtonView playGame;
    private ButtonView wrongButton;
    private ButtonView rightButton;
    private Texture notValidMove;
    private float timecount;
    private float totaleTime;


    protected MakeBoardView(GameStateManager gsm, MakeBoardController controller) {
        super(gsm);
        this.controller = controller;

        background = new Texture("background3.jpeg");
        //board = new Board(10, 10);
        nextButton = new ButtonView("next.png",Battleships.WIDTH/2-100, Battleships.HEIGHT/2,200,75);
        font = new BitmapFont();
        playGame = new ButtonView("Settings.png",Battleships.WIDTH/2-100, Battleships.HEIGHT/2-100,200,75);
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
    public void setpressedOK(boolean pressedOK){
        this.pressedOK = pressedOK;
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
            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT-Gdx.input.getY(), 0);
            System.out.println("Input position. " + x_position + ", " + y_position);
            controller.findShip(controller.getIndex(x_position,y_position));

            //ønsker å få opp feedback om spilleren er feridg med å plassere skip
            if(playGame.getRectangle().contains(touch.x,touch.y)){
                controller.sendBoard();
                gsm.set(new LoadingView(gsm, new LoadingController(controller.getPlayer())));
            }
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
        if(!bool){
            sb.draw(wrongButton.getTexture(),wrongButton.Buttonx,wrongButton.Buttony,wrongButton.Width ,wrongButton.Height);
            sb.draw(notValidMove,wrongButton.Buttonx+100,wrongButton.Buttony,300 ,150);
        }
        else if(bool){
            sb.draw(rightButton.getTexture(),rightButton.Buttonx,rightButton.Buttony,rightButton.Width ,rightButton.Height);
        }
        sb.draw(playGame.getTexture(),playGame.Buttonx,playGame.Buttony,playGame.Width,playGame.Height);
        sb.end();
        drawBoardView();
        drawMarkedShip();

    }
    public void drawBoardView(){
        controller.drawBoardandShips();
    }


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
        if(pressedOK){

        }

    }
}
