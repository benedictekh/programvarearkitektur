package com.mygdx.game.model.ships;

public class CarrierShip extends Ship{

    // the size is based on a vertical direction
    int sizeX = 1;
    int sizeY = 5;

    public CarrierShip(boolean vertical){
        super();
        if (vertical) {
            setSizey(sizeY);
            setSizex(sizeX);
        }
        else{
            setSizey(sizeX);
            setSizex(sizeY);
        }

    }
}
