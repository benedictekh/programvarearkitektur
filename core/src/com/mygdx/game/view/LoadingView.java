package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.GameStateController;

import java.util.concurrent.TimeUnit;

public class LoadingView extends State {

    Texture background;
    Texture loading;
    Texture loading_2;
    boolean witch_texture = true;
    Texture texture;
    float timecount;
    float totaleTime;
    BitmapFont font;
    private boolean musicBool = true;
    private Music loading_song ;
    private ButtonView soundOnButton;
    private ButtonView soundOffButton;
    private ButtonView soundButton;

    /**
     *
     * @param gsm
     * @param gsc
     *
     * the LoadingView is anintermidian stage / state where the handling of the database is done
     * The user will be sent into the game when they are connected to another user on aother divice.
     *
     * QUALITY ATTRIBUTE: USABILITY
     *
     * QUALITY ATTRIBUTE: MODIFIABILITY
     */

    protected LoadingView(GameStateManager gsm, GameStateController gsc) {
        super(gsm, gsc);
        background = new Texture("background1.jpg");
        loading = new Texture("load0.png");
        loading_2 = new Texture("load1.png");
        texture = new Texture("load0.png");
        font = new BitmapFont();
        loading_song =  Gdx.audio.newMusic(Gdx.files.internal("Sounds/sail.mp3"));
        soundOnButton = new ButtonView("soundOn.jpg",Battleships.WIDTH/2+100,Battleships.HEIGHT/2+100,100,100);
        soundOffButton = new ButtonView("soundOff.png",Battleships.WIDTH/2+100,Battleships.HEIGHT/2+100,100,100);
        setMusicButton(soundOnButton);

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT-Gdx.input.getY(), 0);
            if(soundOnButton.getRectangle().contains(touch.x,touch.y)){
                loading_song.pause();
                setMusicButton(soundOffButton);
            }
        }
    }
    public void setMusicButton(ButtonView soundButton){
        this.soundButton = soundButton;
    }

    /**
     * switch between loading images
     */
    private void switchImage(boolean witch_texture){
        if(witch_texture){
            setTexture(loading);
        }
        else {
            setTexture(loading_2);
        }
    }
    private void setTexture(Texture texture) {
        this.texture = texture;
    }

    private Texture getTexture(){
        return this.texture;
    }

    /**
     * updates the loading image making it look like it's spinning
     */

    public void playMusic(){
        loading_song.play();
        loading_song.getVolume();
    }
    public void setMusicTrue(boolean musicBool){
        this.musicBool = musicBool;
        loading_song.play();
        loading_song.setVolume(10f);
    }
    @Override
    public void update(float dt) {
        if(musicBool){
            setMusicTrue(false);
            //playMusic();
        }
            timecount+=dt;
            if (timecount>1)
            {
                if(witch_texture){
                    witch_texture = false;
                }
                else {
                    witch_texture = true;
                }
                switchImage(witch_texture);
                timecount=0;
            }
            // om framen har vart i mer enn 4 sekunder, så skifter den
            //dersom det er to spillere kommer de til playView

            if (gsc.checkPlayersAdded()){
                gsm.set(new MakeBoardView(gsm, gsc));

            }

            if(gsc.checkPlayersReady()){
                gsc.getOpponentBoardFromFirebase();
                //spørsmål: rekker ikke hente ut opponentBrett
                try{
                    TimeUnit.SECONDS.sleep(3);
                }catch(Exception e){
                    e.printStackTrace();
                }
                gsc.initilializeGameFirebase();
                gsm.set(new PlayView(gsm, gsc));
            }
            handleInput();

    }

    /**
     * renders the LoadingView
     */
    @Override
    public void render(SpriteBatch sb) {
        loading_song.play();
        sb.begin();
        sb.draw(background, 0,0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(getTexture(),Battleships.WIDTH/2-175,Battleships.HEIGHT/2-50,350,300);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(3,3);
        font.draw(sb,"Waiting for another player to join", Battleships.WIDTH/2-100,300);
        sb.draw(soundButton.getTexture(),soundOnButton.Buttonx,soundOnButton.Buttony,soundOnButton.Width,soundOnButton.Height);
        sb.end();
    }

    @Override
    public void dispose() {
        loading_song.dispose();

    }
}
