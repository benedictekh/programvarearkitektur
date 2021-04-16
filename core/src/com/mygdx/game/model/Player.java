package com.mygdx.game.model;

import com.mygdx.game.controller.PlayController;

public class Player {

    public String name;
    Boolean thisPlayer;
    Board board;
    int Score = 0;
    String gameId;
    String playerId;

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

    public void setGameId(String gameId){
        this.gameId = gameId;
    }

    public String getGameId(){
        return gameId;
    }

}
