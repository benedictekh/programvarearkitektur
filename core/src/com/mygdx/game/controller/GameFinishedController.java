package com.mygdx.game.controller;

import com.mygdx.game.Battleships;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.ScoreBoard;

import java.util.HashMap;

public class GameFinishedController extends Controller{

    private ScoreBoard scoreboard;
    public static HashMap<String, Integer> printScoreboard;

    public GameFinishedController(Player player){
        super(player);
        scoreboard = new ScoreBoard(player);
        this.updateScoreboard();
    }

    public void updateScoreboard(){
        scoreboard.calculateScore();
        Battleships.firebaseConnector.setScoreboard(scoreboard);
        this.getScoreboard();
    }

    public void getScoreboard(){
        Battleships.firebaseConnector.retrieveScoreboard();
        System.out.println(printScoreboard);
    }

    @Override
    public void update(float dt) {

    }
}
