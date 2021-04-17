package com.mygdx.game;

import com.mygdx.game.model.Player;

import java.util.ArrayList;
import java.util.List;

public interface FirebaseServices {
    //kanskje vi skal implementere dette grensesnittet i battleships og


    public void addPlayer(Player player);
    //public void initializeGame(DataSnapShot waitingRoom);
    //public void waitingRoomListener();
    public void createGame();
    public String turnListener(String gameID);
    public void changeTurn();
    public void playersListener(String gameId);
    public Boolean addTurnListener();
    public ArrayList<List<Integer>> getOpponentBoard();
    public void sendBoard(ArrayList<List<Integer>> board);
    public void boardListener();
    public void sendShot(int x, int y, int newValue);
    public ArrayList<Integer> getOpponentsShot();

}
