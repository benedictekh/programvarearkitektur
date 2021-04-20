package com.mygdx.game.controller;

import com.mygdx.game.Battleships;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Cell;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.ships.Ship;
import com.mygdx.game.view.PlayView;

import java.sql.Array;
import java.sql.Time;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import javax.swing.SwingUtilities;

public class PlayController extends Controller{

    private Board opponentBoard;
    public static boolean myTurn;
    public static boolean shotChanged;
    public static ArrayList<Integer> lastShot;
    private boolean canShoot;
    public static Boolean finishedGame = false;


    public PlayController(Player player) {
        super(player);
        //Battleships.firebaseConnector.sendBoard(player.getBoard().getOpponentBoard());
        //må gjøre om til minuslista senere
        this.opponentBoard = new Board(Battleships.firebaseConnector.getOpponentBoard(), player.getBoard().getSidemargin());
        player.setOpponentBoard(opponentBoard);
        this.myTurn = Battleships.firebaseConnector.addTurnListener();
        canShoot = true;
        Battleships.firebaseConnector.getOpponentsShot();
        Battleships.firebaseConnector.gameFinsihedListener();

    }


    @Override
    public void update(float dt) {

    }

    //må finne en måte å kalle på denne metoden fra androidInterfaceClass
    public void updateShot(){
        if(shotChanged && !myTurn){
            player.getBoard().updateBoard(lastShot.get(0),
                    lastShot.get(1),
                    lastShot.get(2));
        }
        shotChanged = false;
    }



    public void drawBoard(){
        if(myTurn){
            opponentBoard.drawBoard();
            opponentBoard.drawSunkShip();
            opponentBoard.drawUpdatedBoard();
        }
        else {
            player.getBoard().drawBoard();
            player.getBoard().drawShips();
            player.getBoard().drawUpdatedBoard();
        }
    }


    /**
     * computes the index in a double-linked-list from two coordinates
     * finds the cell a person were trying to touch from on a drawn board
     * does not check if the indexes is inside the board
     * @param x_pos the x_coordinate
     * @param y_pos the y_coordinate
     * @return      the indexes for the cell you were trying to touch
     */

    public ArrayList<Integer> getIndex(float x_pos, float y_pos){
        //finds the position on the board
       x_pos = x_pos -player.getBoard().getSidemargin();
       y_pos = y_pos -player.getBoard().getSidemargin();
       ArrayList<Integer>  indexes = new ArrayList<>();
       float t_width = player.getBoard().getWidth();
       //float t_height = board.getTexture().getHeight();
       float cell_width = t_width / player.getBoard().getBoard().size();
       float cell_height = t_width / player.getBoard().getBoard().size();


       indexes.add((int) (x_pos / cell_width));
       indexes.add((int) (y_pos / cell_height));
       return indexes;
    }


    public void shoot(ArrayList<Integer> indexes){
        if (myTurn && canShoot){
            if (this.opponentBoard.shoot(indexes.get(0), indexes.get(1))) {
                setCanShoot(false);
                ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor();
                Runnable task = new Runnable() {
                    @Override
                    public void run() {
                        changeCurrentPlayer();
                        setCanShoot(true);
                    }
                };
                    executor.schedule(task, 3, TimeUnit.SECONDS);

            }
        }
        else{
            System.out.println("Not my turn, can't shoot");

        }
    }
    public void setCanShoot(boolean canShoot){
        this.canShoot = canShoot;
    }


    public Board getBoard(){
        return player.getBoard();
    }

    public boolean isFinished(){
        if (opponentBoard.isFinished()){
            Battleships.firebaseConnector.gameFinished();
            System.out.println("(in if) FinishedGame variable PlayController: " + finishedGame);

        }
        System.out.println("FinishedGame variable PlayController: " + finishedGame);
        return finishedGame;
        //return (opponentBoard.isFinished() || player.getBoard().isFinished());
    }



    public void changeCurrentPlayer(){
        //called when it is next player's turn
        // må si ifra til firebase
        Battleships.firebaseConnector.changeTurn();

    }

    public String turn(){
        if (myTurn){
            return "Nå skal jeg skyte";
        }
        return "Nå skal motstander skyte";
    }


}
