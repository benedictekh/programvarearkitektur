package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.battleships;
import com.mygdx.game.model.Player;

public class InitializeGameView extends State{

    private GameStateManager g;
    private Player player;
    private Texture background;
    BitmapFont font;
    public String name1;
    public ButtonView nextButton;
    public ButtonView loginButton;


    protected InitializeGameView(GameStateManager gsm) {
        super(gsm);
        g = gsm;
        background = new Texture("background1.jpg");
        font = new BitmapFont();
        nextButton = new ButtonView("arrowBlack.jpg",battleships.WIDTH/2+60, 250,50,50);
        loginButton = new ButtonView("clickhere.png",battleships.WIDTH/2-50, 250,100,50);

    }

    @Override
    protected void handleInput() {
        g = gsm;
        if(Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), battleships.HEIGHT - Gdx.input.getY(), 0);
            if (nextButton.getRectangle().contains(touch.x, touch.y)) {
                gsm.set(new LoadingView(gsm));
                System.out.println("test 1");
            } else if(loginButton.getRectangle().contains(touch.x, touch.y)) {
                g = gsm;
                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input(String name) {
                        //setter dette som player name
                        name1 = name;
                        setName(name1);
                        System.out.println(name1);
                    }

                    @Override
                    public void canceled() {
                        System.out.println("ups");
                    }
                },"Username","","");

            }
            else{
                System.out.println("outside");
            }
        }
    }

    public void setName(String name){
        name1 = name;
    }
    public String getName(){
        return name1;
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 255, 1);

        sb.begin();
        sb.draw(background, 0, 0, battleships.WIDTH, battleships.HEIGHT);
        sb.draw(loginButton.getTexture(),loginButton.Buttonx,loginButton.Buttony,loginButton.Width,loginButton.Height);
        if(name1!=null){
            font.draw(sb, "username:  " + getName(),battleships.WIDTH/2-50,battleships.HEIGHT/2);
            sb.draw(nextButton.getTexture(),nextButton.Buttonx,nextButton.Buttony,nextButton.Width,nextButton.Height);
        }
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
