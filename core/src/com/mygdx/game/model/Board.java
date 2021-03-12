package com.mygdx.game.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Board {

    //private Collection<Ship> boardListeners;

    private ArrayList<List<Integer>> board;

    private Cell cell;


    public Board(int size){
        cell = new Cell();
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
        //init ships from Ship class
        // for now
        Random random  = new Random();
        for (int number = 0; number < 5; number ++) {
            int x = random.nextInt(10);
            int y = random.nextInt(10);
            List<Integer> tmpboard = board.get(x);
            tmpboard.set(y, cell.SHIP);
            board.set(x, tmpboard);
        }

        printBoard();
    }



    private void printBoard(){
        for (int row = 0; row < board.size(); row ++){
            System.out.println(board.get(row) + "\n");
        }
    }

    private boolean isValidMove(int x, int y){
        if (( 0 <= x || x < 10) && (0 <= y || y < 10) ){
            // coordiantes is within range, check if cell is already shot at
            int value = board.get(x).get(y);
            return cell.isValidMove(value);
        }
        return false;
    }


    void shoot(int x, int y){
       int value = board.get(x).get(y);
       if (isValidMove(x, y)){
           updateBoard(x, y,cell.setCell(value));
       }
    }

    public void updateBoard(int x, int y, int value){
        List<Integer> tmp = board.get(x);
        tmp.set(y, value);
        board.set(x,tmp);
    }

    public static void main(String[] args) {
        Board test = new Board(10);
    }


}

