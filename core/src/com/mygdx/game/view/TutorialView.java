package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.GameStateController;

public class TutorialView extends State{

    private Texture logo;
    private Texture background;
    private BitmapFont tutorial;
    private ButtonView backButton;
    private Texture gamerules;


    protected TutorialView(GameStateManager gsm, GameStateController gsc) {

        //Et problem her er at jeg ikke vil tegne bagrunnen på nytt, jeg vil bare legge på tutorial

        super(gsm, gsc);
        logo = new Texture("cover.png");
        background = new Texture("background4.jpeg");
        tutorial = new BitmapFont();
        gamerules = new Texture("GameRules.png");
        backButton = new ButtonView("left-arrow.png", 50, Battleships.HEIGHT-120, 100, 100);
    }

    @Override
    protected void handleInput() {
        if (Gdx.input.justTouched()) {

            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT - Gdx.input.getY(), 0);

            if (backButton.getRectangle().contains(touch.x, touch.y)) {
                gsm.pop();
            }
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0,0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(logo,0,-10,500,200);
        tutorial.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        tutorial.getData().setScale(4,4);
        tutorial.draw(sb,"How to play battleships", Battleships.WIDTH/2-300,Battleships.HEIGHT-70);
        sb.draw(backButton.getTexture(),backButton.Buttonx,backButton.Buttony,backButton.Width,backButton.Height);
        sb.draw(gamerules, Battleships.WIDTH/2-750,35,1700,1000 );
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
