package com.mygdx.game.view;//package com.mygdx.game.view; //endret

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;

import com.badlogic.gdx.audio.Sound;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.TextureData;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.GameStateController;
import com.mygdx.game.model.Assets;
import com.mygdx.game.model.ScoreBoard;

public class PlayView extends  State implements FeedbackDelay{

    private Texture background;
    private float x_position;
    private float y_position;
    private GameBoardView gameBoardView;
    //private Board opponentBoard;
    private BitmapFont font = new BitmapFont(); //or use alex answer to use custom font
    private BitmapFont turn = new BitmapFont();
    private boolean feedback = false;

    private Texture logo;
    private ButtonView tutorialButton;
    private TutorialView TutorialView;
    private Texture missed;

    private int i=0;
    private static Sound hitSound;
    private float timecount;



    /**
     * the constructor, sets background
     */
    public PlayView(GameStateManager gsm, GameStateController gsc){
        super(gsm, gsc);
        background = Assets.playBackground;
        logo = Assets.coverLogo;
        missed = Assets.missed;
        this.gameBoardView = new GameBoardView();
        tutorialButton = new ButtonView(Assets.tutorialButton, Battleships.WIDTH/2+380, 135,250,100);
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
            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT-Gdx.input.getY(), 0);

            if(tutorialButton.getRectangle().contains(touch.x, touch.y)) {
                TutorialView = new TutorialView(gsm, gsc);
                gsm.push(TutorialView);
            }

            x_position = Gdx.input.getX();
            y_position = Gdx.input.getY();
            gsc.shoot(gsc.getIndex(x_position, y_position));
            gsc.getScoreBoard().setBoardList(gsc.getOpponentBoard().getBoard());
            gsc.getScoreBoardController().updateScore(gsc.getScoreBoard());
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
        sb.draw(logo, Battleships.WIDTH/2+100, Battleships.HEIGHT-250, 800, 300);
        font.getData().setScale(3, 3);
        turn.getData().setScale(5, 5);
        //font.setColor(Color.BLACK);
        turn.setColor(Color.BLACK);
        font.setColor(Color.BLACK);
        turn.draw(sb, gsc.turn(), Battleships.WIDTH/2+310, 800);
        turn.draw(sb, "Your score: " + gsc.getScoreBoard().getScore() , Battleships.WIDTH / 2 + 300, 600);
        font.draw(sb, "/ - Represents MISS", Battleships.WIDTH / 2 + 320, 420);
        font.draw(sb, "X - Represents HIT", Battleships.WIDTH / 2 + 320, 370);
        sb.draw(tutorialButton.getTexture(),tutorialButton.Buttonx,tutorialButton.Buttony,tutorialButton.Width,tutorialButton.Height);
        if(feedback){
            sb.draw(missed, Battleships.WIDTH/2+40, 40,280,200);
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
