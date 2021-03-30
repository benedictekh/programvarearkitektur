package com.mygdx.game;

import com.mygdx.game.model.Player;

public interface FirebaseServices {
    //kanskje vi skal implementere dette grensesnittet i battleships og

    public void getPlayerScore(String player);
    public void setPlayerScore(String player, int score);
    public void playerScoreValueListener(String player);
    public void addPlayer(Player player);
    //public void initializeGame(DataSnapShot waitingRoom);
    public void waitingRoomListener();
    public void createGame();
}
