package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Color;

public class PatrolShip extends Ship{

    // the size is based on a horizontal direction
    int sizeX = 2;
    int sizeY = 1;




    public PatrolShip(boolean horizontal){
        super(Color.BLUE, -5);
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
