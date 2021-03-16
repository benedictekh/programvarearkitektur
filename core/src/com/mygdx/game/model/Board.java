package com.mygdx.game.model;

import com.mygdx.game.model.ships.DestroyerShip;
import com.mygdx.game.model.ships.Ship;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Board {

    //private Collection<Ship> boardListeners;

    private ArrayList<List<Integer>> board;
    private ArrayList<Ship> ships;

    private Cell cell;


    public Board(int size){
        cell = new Cell();
        ships = new ArrayList<>();
        makeBoard(size);
        System.out.println("Nytt brett kommer nå \n");
        initShips();
    }

    private void makeBoard(int size) {
        board = new ArrayList<List<Integer>>();

        for (int row = 0; row < size; row++) {
            List<Integer> kolonne = new ArrayList<>();
            for (int column = 0; column < 10; column++) {
                kolonne.add(cell.EMPTY);
            }
            board.add(kolonne);
        }
        printBoard();
    }


    private void initShips(){
        // init ships from Ship class
        for (int i = 0 ; i < 3; i++){
            Ship ship = new DestroyerShip(true);
            System.out.println(ship.getLocation());
            ships.add(new DestroyerShip(true));
        }
        for (Ship ship: ships){
            System.out.println("location: " + ship.getLocation());
            // while the ships random location is partly occupied, create a new random location
            while (!isValidLocation(ship.getLocation())) {
                ship.createRandomLocation();
            }
            // the location is valid, update the values on the board
            for (List<Integer> coordinate : ship.getLocation()) {
                int x = coordinate.get(0);
                int y = coordinate.get(1);
                updateBoard(x, y, cell.SHIP);
            }
        }
        printBoard();
    }



    private void printBoard(){
        for (int row = 0; row < board.size(); row ++){
            System.out.println(board.get(row) + "\n");
        }
    }

    private boolean isValidMove(int x, int y){
        if (( x >= 0 || x < 10) && (y >= 0 || y < 10) ){
            System.out.println("Valid coordinates");
            // coordinates is within range, check if cell is already been shot at
            int value = board.get(y).get(x);
            return cell.isValidMove(value);
        }
        return false;
    }


    void shoot(int x, int y){
       int value = board.get(y).get(x);
       if (isValidMove(x, y)){
           System.out.println("Valid move");
           if (cell.isHit(value)){
               for (Ship ship : ships){
                   ship.boardChange(x, y);
               }
           }
           updateBoard(x, y,cell.setCell(value));
       }
    }

    public void updateBoard(int x, int y, int value){
        List<Integer> tmp = board.get(y);
        tmp.set(x, value);
        board.set(y,tmp);
    }
    public boolean isValidLocation(ArrayList<List<Integer>> location){
        for (List<Integer> coordinates : location){
            if (board.get(coordinates.get(1)).get(coordinates.get(0)) == cell.SHIP){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Board test = new Board(10);
        test.shoot(2,1);
        System.out.println("Skutt på 2, 1");
        test.printBoard();

    }
}

