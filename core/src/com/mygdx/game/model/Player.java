package com.mygdx.game.model;

public class Player {

    public String name; //the players username
    Boolean thisPlayer; //is the player you or your opponent
    Board board;    //the players board
    int score = 0;  //the players score

    /**
     * the constructor, sets the name and the boolean, creates a board that is 10x10 and has a sidemargin with size 10
     * @param name  the players username
     * @param thisPlayer  is the player you or your opponent
     */
    public Player(String name, Boolean thisPlayer){
        this.name = name;
        this.thisPlayer = thisPlayer;
        board = new Board(10, 10);
    }

    public Board getBoard(){
        return board;
    }

    public String getName(){
        return name;
    }

}
