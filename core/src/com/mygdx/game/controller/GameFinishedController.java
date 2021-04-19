package com.mygdx.game.controller;

import com.mygdx.game.Battleships;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.ScoreBoard;

public class GameFinishedController extends Controller{

    private ScoreBoard scoreboard;

    public GameFinishedController(Player player){
        super(player);
        scoreboard = new ScoreBoard(player);
        this.updateScoreboard();
    }

    public void updateScoreboard(){
        scoreboard.calculateScore();
        Battleships.firebaseConnector.setScoreboard(scoreboard);
    }

    @Override
    public void update(float dt) {

    }
}
