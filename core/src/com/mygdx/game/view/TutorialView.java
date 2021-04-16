package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;

public class TutorialView extends State{

    private Texture logo;
    private Texture background;
    private BitmapFont tutorial;
    private ButtonView tut_button;


    protected TutorialView(GameStateManager gsm) {

        //Et problem her er at jeg ikke vil tegne bagrunnen på nytt, jeg vil bare legge på tutorial

        super(gsm);
        logo = new Texture("cover.png");
        background = new Texture("background4.jpeg");
        tutorial = new BitmapFont();
    }

    @Override
    protected void handleInput() {

        }


    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(logo,Battleships.WIDTH/2-400,Battleships.HEIGHT-250,800,300);
        tutorial.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tutorial.getData().setScale(4,4);
        tutorial.draw(sb,"Part 1: Set up board", Battleships.WIDTH/2-200,Battleships.HEIGHT-300);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
