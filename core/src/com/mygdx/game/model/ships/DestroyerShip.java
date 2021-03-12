package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.model.Board;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DestroyerShip implements Ship{
    private int size;
    private Boolean isSunk;
    private Texture texture;
    private ArrayList<List<Integer>> location;
    private ArrayList<List<Integer>> shotCoordinates;

    public DestroyerShip(ArrayList<List<Integer>> location){
        //får inn koordinatene der dette skipet skal ligge på brettet
        //burde legge til validering om det er gyldig koordinater
        this.location = location;
    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public boolean isSunk() {
        return false;
    }

    @Override
    public Texture getTexture() {
        return null;
    }

    @Override
    public int getCoordinates() {
        return 0;
    }

    @Override
    public void boardChange(Integer xPos, Integer yPos) {
        //får inn positionene som har blitt skutt denne runden, sjekker om koordinatene til dette skipet ligger på samme posisjon
        ArrayList<Integer> shotPosition = new ArrayList<>(Arrays.asList(xPos, yPos));
        if (location.contains(shotPosition)){
            shotCoordinates.add(shotPosition);
            //sjekke når begge listene er helt like
            //da må skipet gjøres visable
        }
        //x ytterste og y innerste
        //3x3 brett
        /*xPos = 0
          yPos = 1

        board = [[0,1, 0],
                  [0,0, 0],
                  [0,1, 0]] truffet koordinat. (0,1) og (2,1)
         ship = [[0,1], [0,2]]

        */
    }
}
