package com.mygdx.game;

import com.mygdx.game.model.Player;
import com.mygdx.game.model.ScoreBoard;

import java.util.ArrayList;
import java.util.List;

public class GameCodeHolder implements FirebaseServices{

    private String gameId;
    private Integer playerId;
    private static GameCodeHolder instance = null;
    private  FirebaseServices firebaseServices;

    /**
     * Design pattern: Singelton
     */
    private GameCodeHolder(FirebaseServices firebaseServices){
        this.firebaseServices = firebaseServices;
    }

    public static GameCodeHolder getInstance(FirebaseServices firebaseServices){
        if (instance == null){
            instance = new GameCodeHolder(firebaseServices);
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

    @Override
    public void addPlayer(Player player) {
        firebaseServices.addPlayer(player);
    }


    @Override
    public void changeTurn() {
        firebaseServices.changeTurn();
    }

    @Override
    public Boolean addTurnListener() { return firebaseServices.addTurnListener(); }

    @Override
    public ArrayList<List<Integer>> getOpponentBoard() {
        return firebaseServices.getOpponentBoard();
    }

    @Override
    public void sendBoard(ArrayList<List<Integer>> board) {
        firebaseServices.sendBoard(board);
    }

    @Override
    public void boardListener() {
        firebaseServices.boardListener();
    }

    @Override
    public void sendShot(int x, int y, int newValue) {
        firebaseServices.sendShot(x, y, newValue);
    }

    @Override
    public void getOpponentsShot() {
        firebaseServices.getOpponentsShot();
    }

    @Override
    public void setScoreboard(ScoreBoard scoreboard) {
        firebaseServices.setScoreboard(scoreboard);
    }

    @Override
    public void retrieveScoreboard() {
        firebaseServices.retrieveScoreboard();
    }

    @Override
    public void gameFinished(){
        firebaseServices.gameFinished();
    }

    @Override
    public void gameFinsihedListener(){
        firebaseServices.gameFinsihedListener();
    }
}
