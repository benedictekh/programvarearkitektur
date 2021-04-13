package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Ship {

    private int sizex;
    private int sizey;
    private Color color;
    private Boolean isSunk;
    private Texture texture;
    private ArrayList<List<Integer>> location;
    // example location: [[0,0],[0,1],[1,0],[1,1]]
    private ArrayList<List<Integer>> shotCoordinates;

    protected Ship(Color color){
        // får inn koordinatene der dette skipet skal ligge på brettet
        // burde legge til validering om det er gyldig koordinater
        this.color = color;
        isSunk = false;
        shotCoordinates = new ArrayList<List<Integer>>();
    }

    public ArrayList<List<Integer>> getLocation() {
        return this.location;
    }


    public boolean boardChange(Integer xPos, Integer yPos){
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
        System.out.println("Location: " + location);
        System.out.println("ShotCoordinates" + shotCoordinates);
        if (shotCoordinates.containsAll(location)){
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
        int start_x = random.nextInt(10 - sizex + 1);
        int start_y = random.nextInt(10 -sizey + 1);
        this.location = new ArrayList<List<Integer>>();
        for (int x = 0 ; x < sizex; x++){
            this.location.add(Arrays.asList(start_x + x, start_y));
            for (int y = 1 ; y < sizey; y++) {
                this.location.add(Arrays.asList(start_x + x, start_y + y));
            }
        }
        System.out.println("Randomly generated location: " + location);
    }

    public Color getColor(){
        return color;
    }

    protected void setSizex(int size){
        this.sizex = size;
    }

    protected void setSizey(int size){
        this.sizey = size;
    }

    public boolean isSunk(){
        return isSunk;
    }

    public void createNewPosition(int x_coordinate, int y_coordinate){
        int start_x = x_coordinate;
        int start_y = y_coordinate;
        this.location = new ArrayList<List<Integer>>();
        for (int x = 0 ; x < sizex; x++){
            this.location.add(Arrays.asList(start_x + x, start_y));
            for (int y = 1 ; y < sizey; y++) {
                this.location.add(Arrays.asList(start_x + x, start_y + y));
            }
        }
    }


}
