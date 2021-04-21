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


    public ArrayList<List<Integer>> getBoardList() {
        return boardList;
    }

    public void setBoardList(ArrayList<List<Integer>> boardList) {
        this.boardList = boardList;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
