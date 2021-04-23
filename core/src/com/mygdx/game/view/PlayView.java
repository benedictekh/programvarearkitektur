package com.mygdx.game.view;//package com.mygdx.game.view; //endret

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.GameStateController;

public class PlayView extends  State implements FeedbackDelay{

    private Texture background;
    private float x_position;
    private float y_position;
    private GameBoardView gameBoardView;
    //private Board opponentBoard;
    private BitmapFont font = new BitmapFont(); //or use alex answer to use custom font
    private boolean feedback = false;
    private int i=0;
    private static Sound hitSound;
    private float timecount;


    /**
     * the constructor, sets background
     */
    public PlayView(GameStateManager gsm, GameStateController gsc){
        super(gsm, gsc);
        background = new Texture("background3.jpeg");
        this.gameBoardView = new GameBoardView();
        //Battleships.firebaseConnector.sendBoard(player.getBoard().getOpponentBoard());
        //må gjøre om til minuslista senere
        gsc.setOpponentBoard(gsc.getBoardController().createBoardFromOpponent(gsc.getOpponentBoardFromFirebase(), gsc.getPlayer().getBoard().getSidemargin()));
        GameStateController.addFeedbackDelayListener(this);
        hitSound = Gdx.audio.newSound(Gdx.files.internal("Sounds/hitshoot.wav"));

    }

    /**
     * gets the position of where the user clicks
     * "shoots" in the route where the user clicks
     */
    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {
            x_position = Gdx.input.getX();
            y_position = Gdx.input.getY();
            gsc.shoot(gsc.getIndex(x_position, y_position));

        }
        if (gsc.isFinished()){
            gsc.getScoreBoard().setBoardList(gsc.getOpponentBoard().getBoard());
            gsc.getScoreBoardController().updateScoreboard(gsc.getScoreBoard());
            gsm.set(new GameFinishedView(gsm, gsc));
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
        gsc.updateShot();

    }

    /**
     * renders the PlayView
     */
    @Override
    public void render(SpriteBatch sb) {

        sb.begin();
        sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
        font.getData().setScale(3, 3);
        font.draw(sb, gsc.turn(), Battleships.WIDTH/2 + 100, Battleships.HEIGHT / 2-200);
        font.draw(sb, "/ - Means you have missed", Battleships.WIDTH / 2 + 100, Battleships.HEIGHT / 2);
        font.draw(sb, "X - Means you have hit the opponents ship!", Battleships.WIDTH / 2 + 100, Battleships.HEIGHT / 2 + 50);
        if (feedback) {
            font.draw(sb, "You missed! Opponents turn!", Battleships.WIDTH / 2 + 100, Battleships.HEIGHT / 2 - 150);

        }
        sb.end();
        gameBoardView.drawBoardView(gsc.myTurn, gsc.getBoard());

    }

    @Override
    public void dispose() {
        hitSound.dispose();
    }

    public void playSound(){
        if(!feedback && i>0){
            long id = hitSound.play(4f);
            hitSound.setPitch(id,0.6f);
            i=0;
        }
    }

    public void setFeedback(boolean feedback){
        if(!feedback){
            this.i+=1;
            playSound();
        }
        this.feedback = feedback;
    }


    @Override
    public void fireActionDelay(boolean feedbackDelay) {
        setFeedback(feedbackDelay);
    }
}
