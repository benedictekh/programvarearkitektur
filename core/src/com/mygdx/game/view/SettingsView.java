package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.battleships;

import javax.xml.soap.Text;

public class SettingsView extends State{

    private Texture settings;
    private Texture tutorial;


    protected SettingsView(GameStateManager gsm) {

        //Et problem her er at jeg ikke vil tegne bagrunnen på nytt, jeg vil bare legge på tutorial

        super(gsm);
        settings = new Texture("background1.jpg");
        tutorial = new Texture("tutorial.png");

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new MenuView(gsm));
        }

    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();

        sb.draw(settings,0,0, battleships.WIDTH,battleships.HEIGHT);
        sb.draw(tutorial,(battleships.WIDTH/2-100),battleships.HEIGHT/2, battleships.WIDTH/4,battleships.HEIGHT/4);

        sb.end();

    }

    @Override
    public void dispose() {

    }
}
