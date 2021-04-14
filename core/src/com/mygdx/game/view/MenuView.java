package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.MakeBoardController;

public class MenuView extends State{

    private Texture logo;
    private Texture background;

    private ButtonView playbutton;
    private ButtonView initButton;

    private MakeBoardView makeBoardView;

    public MenuView(GameStateManager gsm) {
        super(gsm);

        logo = new Texture("cover.png");
        background = new Texture("background1.jpg");
        playbutton = new ButtonView("playbtn1.png", Battleships.WIDTH/2-100, Battleships.HEIGHT/2,200,75);
        initButton = new ButtonView("Settings.png", Battleships.WIDTH/2-100, 100,200,75);


    }



    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){

            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT-Gdx.input.getY(), 0);

            if(playbutton.getRectangle().contains(touch.x,touch.y)){
                gsm.set(new InitializeGameView(gsm));
            }
            else if(initButton.getRectangle().contains(touch.x,touch.y)) {
                makeBoardView = new MakeBoardView(gsm);
                MakeBoardController.addFeedbackListener(makeBoardView);
                gsm.set(makeBoardView);
            }
            else{
                System.out.println("pressed outside");
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
        sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(logo, Battleships.WIDTH/2-275, Battleships.HEIGHT-150, 500, 200);
        sb.draw(playbutton.getTexture(),playbutton.Buttonx,playbutton.Buttony,playbutton.Width ,playbutton.Height);
        sb.draw(initButton.getTexture(),initButton.Buttonx,initButton.Buttony,initButton.Width,initButton.Height);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
