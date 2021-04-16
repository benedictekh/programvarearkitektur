package com.mygdx.game;

import com.mygdx.game.model.Player;

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

}
