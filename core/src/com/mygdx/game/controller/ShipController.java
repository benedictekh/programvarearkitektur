package com.mygdx.game.controller;

import com.mygdx.game.model.Board;
import com.mygdx.game.model.ships.Ship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class ShipController {

    private Ship markedShip;


     public Ship createShip(){
         return null;
     }

    /**
     * finds the ship that is placed on a selected cell
     * @param indexes   the indexes for the selected cell
     * @return  returns the ship that is occupying the cell, null if the cell doesn't have a ship
     */
    public void findShip(Board board, ArrayList<Integer> indexes){
        if(markedShip == null)
            for(Ship ship: board.getShips()){
                if(ship.getLocation().contains(indexes)){
                    markedShip = ship;
                }
            }

    }

    public void printShipsLocations(Board board) {
        for(Ship ship: board.getShips()){
            System.out.println("location for ship nr " + ship.getShipNr() + ": " + ship.getLocation());
        }
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
    public boolean boardChange(Ship ship, Integer xPos, Integer yPos){
        //checks if we hit this ship
        boolean thisShip = false;
        // gets the positions that has been shot this round. Checks if the coordinates to thisShip is the same as the location
        ArrayList<Integer> shotPosition = new ArrayList<>(Arrays.asList(xPos, yPos));
        if (ship.getLocation().contains(shotPosition)){
            ship.getShotCoordinates().add(shotPosition);
            // this ship is shot
            thisShip = true;
            //sjekke når begge listene er helt like
            //da må skipet gjøres visable
        }
        System.out.println("Location: " + ship.getLocation());
        System.out.println("ShotCoordinates" + ship.getShotCoordinates());
        if (ship.getShotCoordinates().containsAll(ship.getLocation())){
            ship.setSunk(true);
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
    public void createRandomLocation(Ship ship){
        Random random = new Random();
        // finds a random cell for the ship to be placed, this will be the first cell the ship will occupy
        ship.setLocation(new ArrayList<List<Integer>>());
        int start_x = random.nextInt(10 - ship.getSizex() + 1);
        int start_y = random.nextInt(10 - ship.getSizey() + 1);
        //creates a new location list, so you can call the function many times
        // adds the according cells the ship occupies to the list
        for (int x = 0 ; x < ship.getSizex(); x++){
            ship.getLocation().add(Arrays.asList(start_x + x, start_y));
            for (int y = 1 ; y < ship.getSizey(); y++) {
                ship.getLocation().add(Arrays.asList(start_x + x, start_y + y));
            }
        }
        System.out.println("Randomly generated location: " + ship.getLocation());
    }


    /**
     * creates a new location for the ship based on the coordinates of the first cell it should occupy
     * @param x_coordinate  the index of the cell in x-direction
     * @param y_coordinate  the index of the cell in y-direction
     */
    public void createNewPosition(Ship ship, int x_coordinate, int y_coordinate){
        int start_x = x_coordinate;
        int start_y = y_coordinate;
        ship.setLocation(new ArrayList<List<Integer>>());
        for (int x = 0 ; x < ship.getSizex(); x++){
            ship.getLocation().add(Arrays.asList(start_x + x, start_y));
            for (int y = 1 ; y < ship.getSizey(); y++) {
                ship.getLocation().add(Arrays.asList(start_x + x, start_y + y));
            }
        }
    }





    /**
     *
     * @return  the markedShip
     */

    public Ship getMarkedShip() {
        return this.markedShip;
    }

    public void setMarkedShip(Ship ship){
        this.markedShip = ship;
    }









}
