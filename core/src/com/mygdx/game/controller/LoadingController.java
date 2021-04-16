package com.mygdx.game.controller;

import com.mygdx.game.Battleships;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Player;
import com.sun.org.apache.xpath.internal.operations.Bool;

public class LoadingController extends Controller{

    public static Boolean playersAdded = false;
    public static Boolean playersReady = false;


    public LoadingController(Player player){
        super(player);
        //Battleships.firebaseConnector.boardListener();
    }
    @Override
    public void update(float dt) {

    }



    public Boolean checkPlayersAdded(){
        return playersAdded;
    }

    public Boolean checkPlayersReady(){
        playersAdded = false;
        return playersReady;
    }
}
