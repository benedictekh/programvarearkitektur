package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.GameStateController;

public class SinglePlayerView extends State{
    private Texture background;
    private Texture logo;
    private float x_position;
    private float y_position;
    private BitmapFont font = new BitmapFont(); //or use alex answer to use custom font
    private BitmapFont turn = new BitmapFont();
    private GameBoardView gameBoardView;



    public SinglePlayerView(GameStateManager gsm, GameStateController gsc) {
        super(gsm, gsc);
        background = new Texture("background3.jpeg");
        logo = new Texture("logo.png");
        this.gameBoardView = new GameBoardView();
        gsc.setSinglePlayer(true);
        gsc.setScoreBoard(gsc.getScoreBoardController().createNewSingleScoreBoard(gsc.getPlayer(), gsc.getSinglePlayer()));
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT - Gdx.input.getY(), 0);
            x_position = Gdx.input.getX();
            y_position = Gdx.input.getY();
            gsc.shootSingle(gsc.getIndex(x_position, y_position));
            gsc.getScoreBoard().setBoardList(gsc.getBoard().getBoard());
            gsc.getScoreBoardController().updateScore(gsc.getScoreBoard());
        }
        if (gsc.getBoardController().isFinished(gsc.getBoard())){
            gsc.getScoreBoard().setBoardList(gsc.getBoard().getBoard());
            gsc.getScoreBoardController().updateSingleScoreboard(gsc.getScoreBoard());
            gsm.set(new GameFinishedView(gsm, gsc));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(logo, Battleships.WIDTH/2+220, Battleships.HEIGHT-400, 600, 600);
        font.getData().setScale(3, 3);
        turn.getData().setScale(4, 4);
        turn.setColor(Color.BLACK);
        turn.draw(sb, "Your score: " + gsc.getScoreBoard().getScore() , Battleships.WIDTH / 2 + 310, 590);
        font.draw(sb, "/ - Represents MISS", Battleships.WIDTH / 2 + 320, 420);
        font.draw(sb, "X - Represents HIT", Battleships.WIDTH / 2 + 320, 370);

        sb.end();
        gameBoardView.drawBoardView(gsc.getSingleTurn(), gsc.getSingleBoard());

    }

    @Override
    public void dispose() {

    }


}