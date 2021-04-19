package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.Controller;
import com.mygdx.game.controller.PlayController;

public class LoadingView extends State {

    private Texture background;
    private Texture loading;
    private Texture loading_2;
    private boolean witch_texture = true;
    private Texture texture;
    private float timecount;
    private float totaleTime;
    private BitmapFont font;
    private Controller controller;

    /**
     *
     * @param gsm
     * @param controller
     *
     * the LoadingView is anintermidian stage / state where the handling of the database is done
     * The user will be sent into the game when they are connected to another user on aother divice.
     *
     * QUALITY ATTRIBUTE: USABILITY
     *
     * QUALITY ATTRIBUTE: MODIFIABILITY
     */


    protected LoadingView(GameStateManager gsm, Controller controller) {
        super(gsm);
        background = new Texture("background1.jpg");
        loading = new Texture("load0.png");
        loading_2 = new Texture("load1.png");
        texture = new Texture("load0.png");
        font = new BitmapFont();
        this.controller=controller;
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            System.out.println("success!");
        }
    }
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

    @Override
    public void update(float dt) {

            totaleTime += dt;
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
            if(totaleTime > 10){
                gsm.set(new PlayView(gsm, new PlayController(controller.getPlayer())));
            }


    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(getTexture(),Battleships.WIDTH/2-175,Battleships.HEIGHT/2-50,350,300);
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        font.getData().setScale(3,3);
        font.draw(sb,"Please wait", Battleships.WIDTH/2-100,300);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
