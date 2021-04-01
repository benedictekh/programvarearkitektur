package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.battleships;

import java.awt.TextField;

public class LoadingView extends State {

    Texture background;
    Texture loading;
    Texture loading_2;
    boolean witch_texture = true;
    Texture texture;
    float timecount;
    float totaleTime;
    BitmapFont font;


    protected LoadingView(GameStateManager gsm) {
        super(gsm);
        background = new Texture("background1.jpg");
        loading = new Texture("loading.jpeg");
        loading_2 = new Texture("loading1.jpg");
        texture = new Texture("loading2.jpg");
        font = new BitmapFont();
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.isTouched()){
            System.out.println("sucess!");
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
            // om framen har vart i mer enn 4 sekunder, så skifter den
            if(totaleTime > 4){
                gsm.set(new PlayView(gsm));
            }
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0,battleships.WIDTH,battleships.HEIGHT);
        sb.draw(getTexture(),200,200,200,200);
        font.draw(sb,"Please wait",battleships.WIDTH/2-60,190);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
