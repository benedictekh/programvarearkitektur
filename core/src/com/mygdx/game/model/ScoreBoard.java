package com.mygdx.game.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ScoreBoard {

    private ArrayList<List<Integer>> boardList;
    private Player player;

    private int score;

    public ScoreBoard(Player player){
        this.boardList = player.getOpponentBoard().getBoard();
        this.player = player;
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
