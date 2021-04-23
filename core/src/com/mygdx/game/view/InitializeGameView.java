package com.mygdx.game.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.Battleships;
import com.mygdx.game.controller.GameStateController;
import com.mygdx.game.model.Player;

public class InitializeGameView extends State{


    private Player player;
    private Texture background;
    private Texture logo;
    private BitmapFont font;
    public String name1;
    public ButtonView nextButton;
    public ButtonView loginButton;


    /**
     * the constructor sets the background, buttons, logo, and font
     */

    protected InitializeGameView(GameStateManager gsm, GameStateController gsc) {
        super(gsm, gsc);
        logo = new Texture("cover.png");
        background = new Texture("background1.jpg");
        font = new BitmapFont();
        nextButton = new ButtonView("next.png", Battleships.WIDTH/2-150, Battleships.HEIGHT/2-50,300,100);
        loginButton = new ButtonView("username.png", Battleships.WIDTH/2-200, Battleships.HEIGHT/2,420,110);

    }

    /**
     * switches to loadingView when touching the "next" button
     * creates a player with username from the input-field when clicking on login
     */
    @Override
    protected void handleInput() {

        if(Gdx.input.justTouched()) {
            //lagre vektoren som blir trykket
            Vector3 touch = new Vector3(Gdx.input.getX(), Battleships.HEIGHT - Gdx.input.getY(), 0);
            if (loginButton.getRectangle().contains(touch.x, touch.y)) {
                //med den innebgyde funksjonen textinputlistener har
                Gdx.input.getTextInput(new Input.TextInputListener() {
                    @Override
                    public void input(String name) {
                        //creates a new player with the given name
                        gsc.setPlayer(gsc.getPlayerController().createPlayer(name));
                        gsc.getPlayerController().addPlayerFirebase(gsc.getPlayer());
                        name1 = name;
                        setName(name1);
                        System.out.println(name1);
                        System.out.println("brettet som blir hentet av getBoard(): ");
                        gsc.getBoardController().printBoard(gsc.getBoard());
                        gsm.set(new LoadingView(gsm, gsc));
                    }

                    @Override
                    public void canceled() {
                        System.out.println("ups");
                    }
                },"Username","","");


            } //else if(nextButton.getRectangle().contains(touch.x, touch.y)) {

            //}
            else{
                System.out.println("outside");
            }
        }
    }
    /*
    private void createInitializeGameController(String name){
        player = new Player(name, true);
        controller = new InitializeGameController(player);
    }

*/
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

    /**
     * renders the InitializeGameView
     * prints the username of the user if it exists
     */
    @Override
    public void render(SpriteBatch sb) {
        Gdx.gl.glClearColor(0, 0, 255, 1);

        sb.begin();
        sb.draw(background, 0, 0, Battleships.WIDTH, Battleships.HEIGHT);
        sb.draw(logo, Battleships.WIDTH/2-750, Battleships.HEIGHT-500, 1500, 600);
        if(name1==null){
            sb.draw(loginButton.getTexture(),loginButton.Buttonx,loginButton.Buttony,loginButton.Width,loginButton.Height);
        }

        if(name1!=null){
            font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            font.getData().setScale(3,3);
            font.draw(sb, "USERNAME:  " + getName(), Battleships.WIDTH/2-150, Battleships.HEIGHT/2+175);
            sb.draw(nextButton.getTexture(),nextButton.Buttonx,nextButton.Buttony,nextButton.Width,nextButton.Height);
        }
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
