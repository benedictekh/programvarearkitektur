package com.mygdx.game;

public class GameCodeHolder {

    private String gameId;
    private Integer playerId;
    private static GameCodeHolder instance = null;

    private GameCodeHolder(){}

    public static GameCodeHolder getInstance(){
        if (instance == null){
            instance = new GameCodeHolder();
        }
        return instance;
    }

    public void setGameId(String gameId){
        this.gameId = gameId;
    }

    public String getGameId(){
        return gameId;
    }

    public Integer getPlayerId(){
        return playerId;
    }

    public void setPlayerId(Integer playerId){
        this.playerId = playerId;
    }

}
