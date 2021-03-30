package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.math.collision.BoundingBox;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.mygdx.game.battleships;
import com.mygdx.game.model.ships.BattleShip;

import java.awt.Button;
import java.awt.Rectangle;

public class MenuView extends State{

    Texture logo;
    Texture background;
    Texture playBtn;
    Rectangle rectangle_play;
    BoundingBox box_play;
    //Texture initButton;

    private ButtonView playbutton;
    private ButtonView initButton;

    public MenuView(GameStateManager gsm) {
        super(gsm);

        logo = new Texture("cover.png");
        background = new Texture("background1.jpg");
        //playBtn = new Texture("playbtn1.png");
        //initButton = new Texture("Settings.png");

        playbutton = new ButtonView("playbtn1.png",battleships.WIDTH/2-100, battleships.HEIGHT/2,200,75);
        initButton = new ButtonView("Settings.png",battleships.WIDTH/2-100, 100,200,75);


        //rectangle_play = new Rectangle(playBtn.getWidth(),playBtn.getHeight());
        //rectangle_play.setLocation(battleships.WIDTH/2-275, battleships.HEIGHT-150);
        //rectangle_play.contains(200,200);
        //box_play = new BoundingBox(new Vector3(battleships.WIDTH/2-275, battleships.HEIGHT-150,0);
        //vil sette posisjonen til box_play til samme posisjon som plyBtn
        //box_play.set(box_play);
        System.out.println(rectangle_play);

    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched()){
            System.out.println("playbutton"+ playbutton.Buttonx);
            System.out.println("playbutton"+ playbutton.Buttony);
            System.out.println("playbutton"+ playbutton.Width);
            System.out.println("playbutton"+ playbutton.Height);
            System.out.println(Gdx.input.getX());
            System.out.println(Gdx.input.getY());

            Vector3 touch = new Vector3(Gdx.input.getX(), battleships.HEIGHT-Gdx.input.getY(), 0);
            System.out.println("touch" + touch);
            //camera.unproject(touch);
            System.out.println("getrec:" + playbutton.getRectangle());

            if(playbutton.getRectangle().contains(touch.x,touch.y)){
                gsm.set(new PlayView(gsm));
                System.out.println("test 1");
            }

            else if(initButton.getRectangle().contains(touch.x,touch.y)) {
                    gsm.set(new SettingsView(gsm));
            }
            else{
                System.out.println("test2");
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

        sb.draw(background, 0, 0, battleships.WIDTH, battleships.HEIGHT);
        sb.draw(logo,battleships.WIDTH/2-275, battleships.HEIGHT-150, 500, 200);
        sb.draw(playbutton.getTexture(),playbutton.Buttonx,playbutton.Buttony,playbutton.Width ,playbutton.Height);
        //sb.draw(playBtn,rectangle_play.x,rectangle_play.y,200,200);
        sb.draw(initButton.getTexture(),initButton.Buttonx,initButton.Buttony,initButton.Width,initButton.Height);
        sb.end();
    }

    @Override
    public void dispose() {
        //logo.dispose();
        //playBtn.dispose();

    }
}
