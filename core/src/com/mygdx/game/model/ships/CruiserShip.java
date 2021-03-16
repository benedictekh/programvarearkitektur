package com.mygdx.game.model.ships;

public class CruiserShip extends Ship{

    // the size is based on a horizontal direction
    int sizeX = 3;
    int sizeY = 1;

    public CruiserShip(boolean horizontal){
        super();
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
