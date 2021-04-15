package com.mygdx.game.controller;

import com.badlogic.gdx.Game;
import com.mygdx.game.Battleships;
import com.mygdx.game.GameIdHolder;
import com.mygdx.game.model.Player;

public class InitializeGameController extends Controller{


    public InitializeGameController(Player player){
        super(player);
        Battleships.firebaseConnector.addPlayer(player);
    }

    @Override
    public void update(float dt) {

    }
}
