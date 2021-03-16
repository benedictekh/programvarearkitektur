package com.mygdx.game.model.ships;

public class BattleShip extends Ship{

    // the size is based on a horizontal direction
    int sizeX = 2;
    int sizeY = 2;

    public BattleShip(boolean horizontal){
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
