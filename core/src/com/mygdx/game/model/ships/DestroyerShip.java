package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.model.Board;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DestroyerShip extends Ship{
    // this ship has size 2x1 -> the location list cant be larger than 2
    int sizeX = 4;
    int sizeY = 1;



    public DestroyerShip(boolean horizontal) {
        // får inn koordinatene der dette skipet skal ligge på brettet
        // burde legge til validering om det er gyldig koordinater
        super(Color.PINK, -4);
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
