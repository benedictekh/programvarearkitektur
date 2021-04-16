package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public abstract class Ship {
    private int sizex;  //the size of the ship in x-direction
    private int sizey;  //the size of the ship in y-direction
    private Color color;    //the color the ship should be drawn in
    private Boolean isSunk; //keeps track of whether the whole ship is hit
    private Texture texture;    //the texture for the ship
    private ArrayList<List<Integer>> location;  //the different cells the ships occupies on the board
    // example location: [[0,0],[0,1],[1,0],[1,1]]
    private ArrayList<List<Integer>> shotCoordinates;
    private int shipNr;

    /**
     * Sets the color of the ships, creates a new list for the shotCoordinates and sets isSunk to false
     * @param color
     */
    protected Ship(Color color, int shipNr){
        // får inn koordinatene der dette skipet skal ligge på brettet
        // burde legge til validering om det er gyldig koordinater
        this.color = color;
        this.shipNr = shipNr;
        isSunk = false;
        shotCoordinates = new ArrayList<List<Integer>>();
        this.location = new ArrayList<List<Integer>>();

    }

    /**
     *
     * @return the ships location
     */

    public ArrayList<List<Integer>> getLocation() {
        return this.location;
    }


    /**
     * tells the ship that there has been a shot at the cell on xPos and Ypos
     * checks if the shot has hit this ship
     * if it has, the indexes for the cell will be added to the shotCoordinates list
     * if the whole ship is hit, isSunk will be changed to true
     * @param xPos  the first index for the shot
     * @param yPos  the second index for the shot
     * @return
     */
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
    /**
     * creates a random location for the ship and adds it to the location list
     * the size of the location list depends on the size of the ship (sizex and sizey)
     */
    public void createRandomLocation(){
        Random random = new Random();
        // finds a random cell for the ship to be placed, this will be the first cell the ship will occupy
        location = new ArrayList<List<Integer>>();
        int start_x = random.nextInt(10 - sizex + 1);
        int start_y = random.nextInt(10 -sizey + 1);
        //creates a new location list, so you can call the function many times
        // adds the according cells the ship occupies to the list
        for (int x = 0 ; x < sizex; x++){
            this.location.add(Arrays.asList(start_x + x, start_y));
            for (int y = 1 ; y < sizey; y++) {
                this.location.add(Arrays.asList(start_x + x, start_y + y));
            }
        }
        System.out.println("Randomly generated location: " + location);
    }

    /**
     * @return the color of the ship
     */
    public void addLocation(int row, int col){
        this.location.add(Arrays.asList(col, row));
    }

    public Color getColor(){
        return color;
    }

    /**
     * sets the size of the ship in x-direction
     * @param size  the number of cells the ship should occupie in x-direction
     */
    protected void setSizex(int size){
        this.sizex = size;
    }

    /**
     * sets the size of the ship in y-direction
     * @param size  the number of cells the ship should occupie in y-direction
     */
    protected void setSizey(int size){
        this.sizey = size;
    }

    /**
     *
     * @return  a boolean that tells if the ship is sunk or nor
     */
    public boolean isSunk(){
        return isSunk;
    }

    /**
     * creates a new location for the ship based on the coordinates of the first cell it should occupy
     * @param x_coordinate  the index of the cell in x-direction
     * @param y_coordinate  the index of the cell in y-direction
     */
    public int getShipNr(){  return shipNr; }


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
