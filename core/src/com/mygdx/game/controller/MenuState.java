package com.mygdx.game.controller;

import com.badlogic.gdx.Gdx;
import com.mygdx.game.model.Board;

public class MenuState extends Controller{
    MenuState(Board board) {
        super(board);
    }

    @Override
    public void update(float dt) {

    }

    /*
    Her legge funskjoner for det å trykke på knappene for å starte et game pluss for settning
     */

    public boolean startGame(){
        if(Gdx.input.isTouched()) {

        }
        return true;
    }

}
