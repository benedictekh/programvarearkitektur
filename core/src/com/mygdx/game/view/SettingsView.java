package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;

public class SettingsView extends State{

    private Texture settings;
    private Texture tutorial;

    private ButtonView tut_button;


    protected SettingsView(GameStateManager gsm) {

        //Et problem her er at jeg ikke vil tegne bagrunnen på nytt, jeg vil bare legge på tutorial

        super(gsm, null);
        settings = new Texture("background1.jpg");
        tutorial = new Texture("tutorial.png");
        tut_button = new ButtonView("tutorial.png", Battleships.WIDTH/2-100, Battleships.HEIGHT/2,200,75);


    }

    @Override
    protected void handleInput() {
        Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT-Gdx.input.getY(), 0);
        if(tut_button.getRectangle().contains(touch.x,touch.y)){
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

        sb.draw(settings,0,0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(tutorial,(Battleships.WIDTH/2-100), Battleships.HEIGHT/2, Battleships.WIDTH/4, Battleships.HEIGHT/4);

        sb.end();

    }

    @Override
    public void dispose() {

    }
}
