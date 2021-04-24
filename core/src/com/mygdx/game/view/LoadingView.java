package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.GameStateController;
import com.mygdx.game.model.Assets;

import java.util.concurrent.TimeUnit;

public class LoadingView extends State {

    Texture background;
    Texture loading;
    Texture loading_2;
    boolean which_texture = true;
    Texture texture;
    float timecount;
    float totaleTime;
    BitmapFont font;
    private boolean musicBool;
    private Music loading_song ;
    private ButtonView soundOnButton;
    private ButtonView soundOffButton;
    private ButtonView soundButton;
    private boolean music = true;

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
        background = Assets.mainBackground;
        loading = Assets.load0;
        loading_2 = Assets.load1;
        texture = Assets.load0;
        font = new BitmapFont();
        loading_song =  Gdx.audio.newMusic(Gdx.files.internal("Sounds/sail.mp3"));
        musicBool = true;
        soundOnButton = new ButtonView(Assets.soundOn,Battleships.WIDTH/2+850, Battleships.HEIGHT-180, 100, 100);
        soundOffButton = new ButtonView(Assets.soundOff,Battleships.WIDTH/2+850, Battleships.HEIGHT-180, 100, 100);
        //setMusicButton(soundOnButton);


    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT-Gdx.input.getY(), 0);
            if(soundOnButton.getRectangle().contains(touch.x,touch.y)){
                if(music){
                    pauseMusic();
                    setMusic(false);
                    try{
                        TimeUnit.SECONDS.sleep(1);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
                else {
                    playMusic();
                    setMusic(true);
                    try{
                        TimeUnit.SECONDS.sleep(1);
                    }
                    catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }         
        }
    }

    public void setMusic(boolean music){
        this.music = music;
    }
    public void pauseMusic(){
        loading_song.pause();
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
        if(musicBool){
            loading_song.play();
            loading_song.setVolume(1f);
            setIsMusic(false);
        }
    }
    public void setIsMusic(boolean musicBool){
        this.musicBool = musicBool;
    }
    @Override
    public void update(float dt) {
            playMusic();
            timecount+=dt;
            if (timecount>1)
            {
                if(which_texture){
                    which_texture = false;
                }
                else {
                    which_texture = true;
                }
                switchImage(which_texture);
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
        sb.begin();
        sb.draw(background, 0,0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(getTexture(),Battleships.WIDTH/2-175,Battleships.HEIGHT/2-50,350,300);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(3,3);
        font.draw(sb,"Waiting for opponent", Battleships.WIDTH/2-200,300);
        if(music){
            sb.draw(soundOnButton.getTexture(),soundOnButton.Buttonx,soundOnButton.Buttony,soundOnButton.Width,soundOnButton.Height);
        }
        else if(!music){
            sb.draw(soundOffButton.getTexture(),soundOffButton.Buttonx,soundOffButton.Buttony,soundOffButton.Width,soundOffButton.Height);
        }
        sb.end();
    }

    @Override
    public void dispose() {
        loading_song.dispose();
    }
}
