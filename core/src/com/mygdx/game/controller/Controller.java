package com.mygdx.game.controller;

import com.mygdx.game.model.Board;
import com.mygdx.game.model.Player;

public abstract class Controller {

    private Player player;

    Controller(Player player) {
        this.player = player;
    }

    public Player getPlayer(){
        return this.player;
    }


}
