package com.mygdx.game.model;

import com.mygdx.game.controller.PlayController;

public class Player {

    public String name;
    Boolean thisPlayer;
    Board board;
    int Score = 0;
    String gameId;
    String playerId;

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
