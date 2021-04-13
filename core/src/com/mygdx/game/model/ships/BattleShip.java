package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Color;

public class BattleShip extends Ship{

    // the size is based on a horizontal direction
    int sizeX = 2;
    int sizeY = 2;


    public BattleShip(boolean horizontal){
        super(Color.BROWN, -1);
        if (horizontal) {
            setSizey(sizeY);
            setSizex(sizeX);
        }
        else{
            setSizey(sizeX);
            setSizex(sizeY);
        }


    }
}
