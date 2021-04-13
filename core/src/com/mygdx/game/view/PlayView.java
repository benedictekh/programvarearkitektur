package com.mygdx.game.view;//package com.mygdx.game.view; //endret

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.PlayController;
import com.mygdx.game.model.Player;

public class PlayView extends  State {

    private Texture background;
    private Texture board;
    private float x_position;
    private float y_position;
    private PlayController controller;
    private Player current;
    private BitmapFont font = new BitmapFont(); //or use alex answer to use custom font


    public PlayView(GameStateManager gsm, PlayController controller){
        super(gsm);
        background = new Texture("background.PNG");
        this.controller = controller;
        //controller = new BoardController( new Board(10, 10));
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
        sb.draw(background,0,0, Battleships.WIDTH, Battleships.HEIGHT);
        //sb.draw(board,0,0,battleships.WIDTH,battleships.HEIGHT);
        font.draw(sb, current.getName(), Battleships.WIDTH - 50, Battleships.HEIGHT -10);
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
