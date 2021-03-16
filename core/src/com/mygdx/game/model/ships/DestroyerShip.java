package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.model.Board;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class DestroyerShip implements Ship{
    // this ship has size 2x2 -> the location list cant be larger than 4
    private int sizex = 3;
    private int sizey = 2;
    private Boolean isSunk;
    private Texture texture;
    private ArrayList<List<Integer>> location;
    // eksample location: [[0,0],[0,1],[1,0],[1,1]]
    private ArrayList<List<Integer>> shotCoordinates;

    public DestroyerShip(){
        // får inn koordinatene der dette skipet skal ligge på brettet
        // burde legge til validering om det er gyldig koordinater
        createRandomLocation();
        isSunk = false;
        shotCoordinates = new ArrayList<List<Integer>>();
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
    public ArrayList<List<Integer>> getLocation() {
        return this.location;
    }

    @Override
    public boolean boardChange(Integer xPos, Integer yPos) {
        //checks if we hit this ship
        boolean thisShip = false;
        // gets the positions that has been shot this round. Checks if the coordinates to thisShip is the same as the location
        ArrayList<Integer> shotPosition = new ArrayList<>(Arrays.asList(xPos, yPos));
        if (location.contains(shotPosition)){
            shotCoordinates.add(shotPosition);
            // this ship is shot
            thisShip = true;
            //sjekke når begge listene er helt like
            //da må skipet gjøres visable
        }
        if (shotCoordinates == location){
            isSunk = true;
        }
        return thisShip;
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

    public void createRandomLocation(){
        Random random = new Random();
        int startx = random.nextInt(10 - sizex);
        int starty = random.nextInt(10 -sizey);
        location = new ArrayList<List<Integer>>();
        for (int x = 0 ; x < sizex; x++){
            location.add(Arrays.asList(startx + x, starty));
            for (int y = 1 ; y < sizey; y++) {
                location.add(Arrays.asList(startx + x, starty + y));
            }

        }
    }
}
