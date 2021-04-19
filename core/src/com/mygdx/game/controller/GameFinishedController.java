package com.mygdx.game.controller;

import com.mygdx.game.Battleships;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.ScoreBoard;

import java.util.HashMap;

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

    public void getScoreboard(){
        HashMap<String, String> scoreboard = Battleships.firebaseConnector.retrieveScoreboard();
        System.out.println(scoreboard);
    }

    @Override
    public void update(float dt) {

    }
}
