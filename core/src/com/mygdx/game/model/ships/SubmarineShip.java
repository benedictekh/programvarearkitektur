package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Color;

public class SubmarineShip extends Ship{

    // the size is based on a horizontal direction
    int sizeX = 3;
    int sizeY = 2;



    public SubmarineShip(boolean horizontal){

        super(Color.RED);
        if (horizontal) {
            setSizey(sizeY);
            setSizex(sizeX);
        }
        else{
            setSizey(sizeX);
            setSizex(sizeY);
        }
        createRandomLocation();

    }
}
