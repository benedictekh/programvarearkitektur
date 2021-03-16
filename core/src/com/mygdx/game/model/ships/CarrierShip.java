package com.mygdx.game.model.ships;

public class CarrierShip extends Ship{

    // the size is based on a horizontal direction
    int sizeX = 5;
    int sizeY = 1;

    public CarrierShip(boolean horizontal){
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
