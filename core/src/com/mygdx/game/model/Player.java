package com.mygdx.game.model;

public class Player {

    public String name;
    Boolean thisPlayer;
    Board board;
    int Score = 0;

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
