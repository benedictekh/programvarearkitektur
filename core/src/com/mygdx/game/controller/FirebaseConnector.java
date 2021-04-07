package com.mygdx.game.controller;

import com.mygdx.game.FirebaseServices;
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
    public void addWaitingroomLisenerOnce() {

    }

    @Override
    public void createGame() {

    }
}
