package com.mygdx.game.controller;

import com.mygdx.game.Battleships;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.ships.Ship;
import com.mygdx.game.view.Feedback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class MakeBoardController extends Controller{

    Ship markedShip = null;
    private ArrayList<List<Integer>> location;

    private static Collection<Feedback> feedbackListeners = new ArrayList<Feedback>();


    public MakeBoardController(Player player) {
        super(player);
    }


    @Override
    public void update(float dt) {

    }

    public void sendBoard(){
        createInitalizeOpponentBoard();
        Battleships.firebaseConnector.sendBoard(player.getBoard().getOpponentBoard());
        Battleships.firebaseConnector.boardListener();
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

    /**
     * finds the ship located at a specific index and changes the attribute @markedShip
     * finds a new ship if @markedship is null
     * @param indexes   the indexes of the cell you want to find a ship
     */

    public void findShip(ArrayList<Integer> indexes){
        if(markedShip == null ){
            this.markedShip = player.getBoard().findShip(indexes);
        }
        player.getBoard().printShipsLocations();
    }

    /**
     *
     * @return  the markedShip
     */

    public Ship getMarkedShip() {
        return this.markedShip;
    }

    /**
     * draws the marked ship as a square
     */

    public void drawMarkedShip() {
        player.getBoard().drawShipSquare(this.markedShip);
    }

    /**
     * draws both the board and the ships
     */
    public void drawBoardandShips() {
        player.getBoard().drawBoard();
        player.getBoard().drawShips();
    }

    /**
     * moves the marked ship to a new position, if the new position is valid and inside the board
     * removes the ships position from the board (ArrayList<List<Integer>>) and adds the new position to the boars
     * @param new_x the x_ccordinate of where you want to move the ship
     * @param new_y the y_coordinate of where you want to move the ship
     * @param location  ??
     */
    public void moveShip(int new_x, int new_y, ArrayList<List<Integer>> location) {
        // finds the index based on the coordinates of the touch
        ArrayList<Integer> index= getIndex(new_x,new_y);
        // saves the first position of the current (old) location of the markedShip
        List<Integer> first_position = markedShip.getLocation().get(0);
        //removes the ship from the board (changes the 1-value into 0)
        player.getBoard().removeShipPosition(markedShip.getLocation());
        //adds the new position to the board (replaces the 0-values on the cells with 1)
        markedShip.createNewPosition(index.get(0), index.get(1));
        // checks if the new location of the markedShip is inside the board
        if(!player.getBoard().isInsideBoard(markedShip.getLocation())){
            firefeedbackFalse();
            markedShip.createNewPosition(first_position.get(0),first_position.get(1));
        }
        // checks if the ship can be moved to the new location
        else if(!player.getBoard().isValidLocation(markedShip.getLocation())){
            firefeedbackFalse();
            markedShip.createNewPosition(first_position.get(0),first_position.get(1));
        }

        else {
            firefeedbackTrue();
        }

        player.getBoard().addShipPosition(markedShip.getLocation());
        //sets the markedShip value to null so we can select new ships to move :D
        markedShip = null;
        player.getBoard().printBoard();
    }

    public void createInitalizeOpponentBoard(){
        player.getBoard().makeInitalizeOpponentBoard();
    }

    public static void addFeedbackListener(Feedback feedbackListener) {
        feedbackListeners.add(feedbackListener);
    }
    /*

    public void removeCrashListener(Feedback feedbackListener) {
        feedbackListeners.remove(feedbackListener);
    }

     */


    public static void firefeedbackTrue() {
        for (Feedback feedbackListener: feedbackListeners) {
            feedbackListener.fireAction(true);
        }
    }
    public void firefeedbackFalse() {
        for (Feedback feedbackListener: feedbackListeners) {
            feedbackListener.fireAction(false);
        }
    }

}
