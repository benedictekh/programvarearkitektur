package com.mygdx.game.view;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.Stack;

public class GameStateManager {
    /**
     * The game state manager (gsm) has the overview of the state.
     * This is out state pattern whitch controls what view is used and showed on the screen.
     * Witch state that is used is controlled by making a Stack, where you will have access to the top element/"plate"
     * Push and Pop is used when the set fuction is used. Here you set the new state state by sending in the gsm.
     * The state abstract class is implemented to all the views.
     */

    private Stack<State> states;

    public GameStateManager(){

        states = new Stack<State>();
    }


    public void push(State state){

        states.push(state);
    }

    public void pop(){

        states.pop().dispose();
    }

    public void set(State state){
        states.pop().dispose();
        states.push(state);
    }

    public void update(float dt){

        states.peek().update(dt);
    }

    public void render(SpriteBatch sb){

        states.peek().render(sb);
    }
}