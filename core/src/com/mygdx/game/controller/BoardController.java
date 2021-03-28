package com.mygdx.game.controller;

import com.mygdx.game.model.Board;
import com.mygdx.game.model.Player;
import com.mygdx.game.view.PlayView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

public class BoardController extends Controller{

    Player player1;
    Player player2;
    Player currentPlayer;

    public BoardController(Board board) {

        super(board);
        player1 = new Player("Helena", true);
        player2 = new Player("Ane", false);
        currentPlayer = player1;
    }

    @Override
    public void update(float dt) {

    }

    public ArrayList<Integer> getIndex(float x_pos, float y_pos){
        //finds the position on the board
        System.out.println("Sidemargin: " + currentPlayer.getBoard().getSidemargin());
        x_pos = x_pos -currentPlayer.getBoard().getSidemargin();
        y_pos = y_pos -currentPlayer.getBoard().getSidemargin();


        ArrayList<Integer>  indexes = new ArrayList<>();
        System.out.println("Width: " + currentPlayer.getBoard().getWidth());
       float t_width = currentPlayer.getBoard().getWidth();
       //float t_height = board.getTexture().getHeight();
       float cell_width = t_width / currentPlayer.getBoard().getBoard().size();
       float cell_height = t_width / currentPlayer.getBoard().getBoard().size();


       indexes.add((int) (x_pos / cell_width));
       indexes.add((int) (y_pos / cell_height));
       System.out.println("Indexes: " +indexes);
       return indexes;
    }

    public void shoot(ArrayList<Integer> indexes){
        if (currentPlayer.getBoard().shoot(indexes.get(0), indexes.get(1))) {
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

    public Board getBoard(){
        return currentPlayer.getBoard();
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

    public Player getPlayer() {
        return currentPlayer;
    }

    public void changeCurrentPlayer(){
        System.out.println("Next players turn!");
        if (currentPlayer == player1){
            currentPlayer = player2;
        }
        else{
            currentPlayer = player1;
        }
    }
}
