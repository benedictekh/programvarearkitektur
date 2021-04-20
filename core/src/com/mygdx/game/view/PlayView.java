package com.mygdx.game.view;//package com.mygdx.game.view; //endret

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.Controller;
import com.mygdx.game.controller.GameFinishedController;
import com.mygdx.game.controller.PlayController;
import com.mygdx.game.model.Player;

public class PlayView extends  State implements FeedbackDelay{

    private Texture background;
    private float x_position;
    private float y_position;
    private PlayController controller;
    private BitmapFont font = new BitmapFont(); //or use alex answer to use custom font
    private boolean myTurn = false;
    private float time;


    public PlayView(GameStateManager gsm, PlayController controller){
        super(gsm);
        background = new Texture("background3.jpeg");
        this.controller = controller;
    }


    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            x_position = Gdx.input.getX();
            y_position = Gdx.input.getY();
            controller.shoot(controller.getIndex(x_position, y_position));

        }
        if (controller.isFinished()){
            gsm.set(new GameFinishedView(gsm, new GameFinishedController(controller.getPlayer())));
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
        controller.updateShot();
    }

    @Override
    public void render(SpriteBatch sb) {

        sb.begin();
        sb.draw(background,0,0, Battleships.WIDTH, Battleships.HEIGHT);
        //sb.draw(board,0,0,battleships.WIDTH,battleships.HEIGHT);
        font.draw(sb, controller.getPlayer().getName(), Battleships.WIDTH - 50, Battleships.HEIGHT -10);
        if(myTurn){
            font.draw(
                    sb,
                    controller.getPlayer().getName() + ", Good move. The opontents turn.",
                    Battleships.WIDTH - 50,
                    Battleships.HEIGHT -10);
        }
        sb.end();
        controller.drawBoard();

    }

    @Override
    public void dispose() {

    }
    public void setMyTurn(boolean myTurn){
        this.myTurn = myTurn;
    }

    @Override
    public void fireActionDelay(boolean myTurn) {
        setMyTurn(myTurn);
    }
    /*
    private String turn(){
        if (controller.myTurn){
            return "Nå skal jeg skyte";
        }
        return "Nå skal motstander skyte";
    }

     */


}
