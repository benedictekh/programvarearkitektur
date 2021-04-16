package com.mygdx.game.controller;

import com.mygdx.game.FirebaseServices;
import com.mygdx.game.GameIdHolder;
import com.mygdx.game.model.Player;

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
    public Boolean changeTurn() {
        return firebaseServices.changeTurn();
    }



    @Override
    public void playersListener(String gameId) {
        firebaseServices.playersListener(gameId);
    }
}
