package com.mygdx.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScoreBoard {

    private ArrayList<List<Integer>> boardList;
    private Player player;
    private HashMap<String, Integer> playerScore;
    private int score;

    public ScoreBoard(Player player){
        this.boardList = player.getBoard().getOpponentBoard();
        this.player = player;
        this.playerScore = new HashMap<>();
        this.score = 0;
    }

    public String getName(){
        return player.getName();
    }

    public int getScore(){
        return score;
    }

    public void calculateScore(){
        for (List<Integer> board : boardList){
            for (Integer b : board){
                if (b == 2){
                    this.score += -20;
                    //a miss
                }else if(b == 3){
                    //a hit
                    this.score += 100;
                }
            }
        }
    }




}
