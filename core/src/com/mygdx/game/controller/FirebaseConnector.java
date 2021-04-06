package com.mygdx.game.controller;

import com.mygdx.game.FirebaseServices;
import com.mygdx.game.model.Player;

public class FirebaseConnector implements FirebaseServices{
    FirebaseServices firebaseServices;

    public FirebaseConnector(FirebaseServices firebaseServices){
        this.firebaseServices = firebaseServices;
    }


    @Override
    public void getPlayerScore(String player) {

    }

    @Override
    public void setPlayerScore(String player, int score) {

    }

    @Override
    public void playerScoreValueListener(String player) {

    }

    @Override
    public void addPlayer(Player player) {
        firebaseServices.addPlayer(player);
    }

    @Override
    public void addWaitingroomLisenerOnce() {

    }

    @Override
    public void createGame() {

    }
}
