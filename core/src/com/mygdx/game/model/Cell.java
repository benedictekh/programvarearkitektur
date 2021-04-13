package com.mygdx.game.model;

import com.badlogic.gdx.graphics.Texture;

public class Cell {

    public static int SHIP = 1;
    public static int MISS = 2;
    public static int EMPTY = 0;
    public static int HIT = 3;

    private Texture hit;
    private Texture miss;

    public boolean isValidMove(int value){
        if  (value == HIT || value == MISS) {
            System.out.println("Already shot here");
            return false;
        }
        return true;
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
