package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Color;

public class CarrierShip extends Ship{

    // the size is based on a horizontal direction
    int sizeX = 5;
    int sizeY = 1;


    public CarrierShip(boolean horizontal){
        super(Color.YELLOW, -2);
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