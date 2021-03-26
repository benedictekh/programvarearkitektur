package com.mygdx.game;

public interface FirebaseServices {
    //kanskje vi skal implementere dette grensesnittet i battleships og

    public String getPlayerName();
    public void getPlayerScore(String player);
    public void setPlayerScore(String player, int score);
    public void playerScoreValueListener(String player);
}
