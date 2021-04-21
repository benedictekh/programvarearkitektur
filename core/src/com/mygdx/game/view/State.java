package com.mygdx.game.view;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector3;
import com.mygdx.game.controller.Controller;
import com.mygdx.game.controller.GameStateController;
import com.mygdx.game.view.GameStateManager;

public abstract class State {

    /*
    State is an abstract class that is implemented in all the View classes. With the gsm we can controll
     whitch state that is showing on the screen.
     */
    protected OrthographicCamera cam;
    protected Vector3 mouse;
    protected GameStateManager gsm;
    protected GameStateController gsc;

    protected State(GameStateManager gsm, GameStateController gsc){
        this.gsc = gsc;
        this.gsm = gsm;
        cam = new OrthographicCamera();
        mouse = new Vector3();
    }

    //takes input and send it to the controller (if controller is involved)
    protected abstract void handleInput();

    //updates the model (by using the models update function)
    public abstract void update(float dt);

    public abstract void render(SpriteBatch sb);

    public abstract void dispose();
}