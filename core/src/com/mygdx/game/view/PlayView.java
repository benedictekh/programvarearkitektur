package com.mygdx.game.view;//package com.mygdx.game.view; //endret

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.battleships;
import com.mygdx.game.model.Board;
import com.mygdx.game.view.GameStateManager;
import com.mygdx.game.view.State; //endret

public class PlayView extends  State {

    private int width = battleships.WIDTH;
    private int height = battleships.HEIGHT;

    private Board board;
    private Pixmap map;
    private GameStateManager gsm;
    private Texture background;
    /*
    private Player player;
    private Cell cell;
    private DestroyerShip destroyerShip;
    private CarrierShip carrierShip;
    private BattleShip battleShip;
    private CruiserShip cruiserShip;
    private SubmarineShip submarineShip;

     */

    public PlayView(GameStateManager gsm, Board board){
        super(gsm);

        background = new Texture("ocean.jpeg");

        //map = new Pixmap(width, height, Pixmap.Format.RGBA8888);
    }


    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background,width,height);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
