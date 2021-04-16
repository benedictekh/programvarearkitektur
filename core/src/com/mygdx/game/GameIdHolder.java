package com.mygdx.game;

public class GameIdHolder {

    public String gameId;
    public Integer playerId;
    private static GameIdHolder instance = null;

    private GameIdHolder(){}

    public static GameIdHolder getInstance(){
        if (instance == null){
            instance = new GameIdHolder();
        }
        return instance;
    }
}
