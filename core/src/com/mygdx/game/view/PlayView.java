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
    private String feedback;


    public PlayView(GameStateManager gsm, PlayController controller){
        super(gsm);
        background = new Texture("background3.jpeg");
        this.controller = controller;
        feedback = null;
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
        font.getData().setScale(3,3);
        font.draw(sb, controller.turn(), Battleships.WIDTH-300,Battleships.HEIGHT/2 );
        if(feedback != null){
            font.draw(sb, this.feedback,Battleships.WIDTH/2+100,Battleships.HEIGHT/2 );
        }
        sb.end();
        controller.drawBoard();


    }

    @Override
    public void dispose() {

    }
    public void setFeedback(String feedback){
        this.feedback = feedback;
    }

    @Override
    public void fireActionDelay(String string) {
        setFeedback(string);
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
