package com.mygdx.game;

import com.mygdx.game.model.Player;
import com.mygdx.game.model.ScoreBoard;

import java.util.ArrayList;
import java.util.List;

public interface FirebaseServices {
    //kanskje vi skal implementere dette grensesnittet i battleships og


    public void addPlayer(Player player);
    public void createGame();
    public void changeTurn();
    public Boolean addTurnListener();
    public ArrayList<List<Integer>> getOpponentBoard();
    public void sendBoard(ArrayList<List<Integer>> board);
    public void boardListener();
    public void sendShot(int x, int y, int newValue);
    public void getOpponentsShot();
    public void setScoreboard(ScoreBoard scoreboard);

}
