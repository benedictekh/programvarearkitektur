package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.BoardController;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Player;

public class InitializeGameView extends State{

    private GameStateManager g;
    private Player player;
    private Texture background;
    BitmapFont font;
    public String name1;
    public ButtonView nextButton;
    public ButtonView loginButton;
    private BoardController controller;


    protected InitializeGameView(GameStateManager gsm) {
        super(gsm);
        g = gsm;
        background = new Texture("background1.jpg");
        font = new BitmapFont();
        nextButton = new ButtonView("next.png", Battleships.WIDTH/2-100, Battleships.HEIGHT/2-50,200,75);
        loginButton = new ButtonView("click.png", Battleships.WIDTH/2-150, Battleships.HEIGHT/2,300,200);
    }

    @Override
    protected void handleInput() {
        g = gsm;
        if(Gdx.input.justTouched()) {
            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT - Gdx.input.getY(), 0);
            if (nextButton.getRectangle().contains(touch.x, touch.y)) {
                //vil bli lagt til i databasen og vente på motspiller i Loadingview
                gsm.set(new LoadingView(gsm, controller));
                System.out.println("test 1");
            } else if(loginButton.getRectangle().contains(touch.x, touch.y)) {
                g = gsm;
                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input(String name) {
                        //vil opprette player objekt
                        //setter dette som player name

                        //tester å legge til en person i db

                        createBoardController(name);
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

    private void createBoardController(String name){
        this.controller = new BoardController( new Board(10, 10), new Player(name, true));

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
        sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
        if(name1==null){
            sb.draw(loginButton.getTexture(),loginButton.Buttonx,loginButton.Buttony,loginButton.Width,loginButton.Height);
        }

        if(name1!=null){
            font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            font.getData().setScale(2,2);
            font.draw(sb, "USERNAME:  " + getName(), Battleships.WIDTH/2-100, Battleships.HEIGHT/2+80);
            sb.draw(nextButton.getTexture(),nextButton.Buttonx,nextButton.Buttony,nextButton.Width,nextButton.Height);
        }
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
