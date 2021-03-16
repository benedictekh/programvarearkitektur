package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.model.Board;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DestroyerShip extends Ship{
    // this ship has size 3x2 -> the location list cant be larger than 4
    int sizeX = 3;
    int sizeY = 2;

    public DestroyerShip(boolean vertical) {
        // får inn koordinatene der dette skipet skal ligge på brettet
        // burde legge til validering om det er gyldig koordinater
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
