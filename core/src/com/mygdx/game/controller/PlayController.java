package com.mygdx.game.controller;

import com.mygdx.game.Battleships;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Cell;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.ships.Ship;
import com.mygdx.game.view.PlayView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

public class PlayController extends Controller{

    //Player player2;
    private String gameId;
    private Board opponentBoard;
    private boolean myTurn;

    public PlayController(Player player) {
        super(player);
        System.out.println("fra playcontroller" + player.getGameId());
        this.opponentBoard = new Board(player.getBoard().getOpponentBoard(), player.getBoard().getSidemargin());
        Battleships.firebaseConnector.playersListener(player.getGameId());

        //this.opponentBoard = new Board();
        //player = player;
        //må finne en bedre måte å få firebaseConnector inn her
        this.myTurn = Battleships.firebaseConnector.addTurnListener();
        //player2 = new Player("Ane", false);
    }

    @Override
    public void update(float dt) {

    }

    public void setGameId(String gameId){
        this.gameId = gameId;
    }

    // Kalle på firebase inne i denne
    public void getOpponentBoard(){
    }

    public void drawBoard(){
        if(myTurn){
            opponentBoard.drawBoard();
            //opponentBoard.drawShips();
            opponentBoard.drawUpdatedBoard();
        }
        else {
            player.getBoard().drawBoard();
            player.getBoard().drawShips();
            player.getBoard().drawUpdatedBoard();
        }
    }


    public ArrayList<Integer> getIndex(float x_pos, float y_pos){
        //finds the position on the board
        System.out.println("Sidemargin: " + player.getBoard().getSidemargin());
        x_pos = x_pos -player.getBoard().getSidemargin();
        y_pos = y_pos -player.getBoard().getSidemargin();


        ArrayList<Integer>  indexes = new ArrayList<>();
        System.out.println("Width: " + player.getBoard().getWidth());
       float t_width = player.getBoard().getWidth();
       //float t_height = board.getTexture().getHeight();
       float cell_width = t_width / player.getBoard().getBoard().size();
       float cell_height = t_width / player.getBoard().getBoard().size();


       indexes.add((int) (x_pos / cell_width));
       indexes.add((int) (y_pos / cell_height));
       System.out.println("Indexes: " +indexes);
       return indexes;
    }


    public void shoot(ArrayList<Integer> indexes){
        if (myTurn){
            if (player.getBoard().shoot(indexes.get(0), indexes.get(1))) {
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        changeCurrentPlayer();
                    }
                };
                executor.schedule(task, 1, TimeUnit.SECONDS);
            }
        }

    }


    public Board getBoard(){
        return player.getBoard();
    }

    public boolean isFinished(){
        ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
        Callable<Boolean> c1 = new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return getBoard().isFinished();
            }
        };

        executor.schedule(c1, 1, TimeUnit.SECONDS);
        return getBoard().isFinished();

    }

    //funksjon som heter getTurn - henter status på firebase turn variabelen og sammenligner om det er this.player.name


    public void changeCurrentPlayer(){
        //called when it is next player's turn
        // må si ifra til firebase
        Battleships.firebaseConnector.changeTurn();
        this.myTurn = Battleships.firebaseConnector.addTurnListener();
        System.out.println("turn in controller: " + myTurn);

    }

}
