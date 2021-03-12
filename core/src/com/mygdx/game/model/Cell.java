package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;

public class Cell {

    static int SHIP = 1;
    static int MISS = 2;
    static int EMPTY = 0;
    static int HIT = 3;

    private Texture hit;
    private Texture miss;

    public boolean isValidMove(int value){
        return !(value == HIT || value == MISS);
    }

    public boolean isHit(int value){
        return (value == SHIP);
    }

    public int setCell(int value){
        int newValue;
        if (isHit(value)){
            newValue = HIT;
        }
        else {
            newValue = MISS;
        }
        System.out.println("new value: "+ newValue);
        return newValue;
    }
}
