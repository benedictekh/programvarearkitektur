package com.mygdx.game.controller;

import com.mygdx.game.FirebaseServices;
import com.mygdx.game.GameIdHolder;
import com.mygdx.game.model.Player;

import java.util.ArrayList;
import java.util.List;

public class FirebaseConnector implements FirebaseServices{
    FirebaseServices firebaseServices;


    public FirebaseConnector(FirebaseServices firebaseServices){
        this.firebaseServices = firebaseServices;
    }

    @Override
    public void addPlayer(Player player) {
        firebaseServices.addPlayer(player);
    }


    @Override
    public void createGame() {

    }

    @Override
    public String turnListener(String gameID) {
        return null;
    }

    @Override
    public void changeTurn() {
        firebaseServices.changeTurn();
    }



    @Override
    public void playersListener(String gameId) {
        firebaseServices.playersListener(gameId);
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
    public ArrayList<Integer> getOpponentsShot() {
        return null;
    }

}
