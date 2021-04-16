package com.mygdx.game.controller;

import com.mygdx.game.model.Board;
import com.mygdx.game.model.ships.Ship;
import com.mygdx.game.view.Feedback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;



public class MakeBoardController extends Controller{

    Ship markedShip = null;
    private ArrayList<List<Integer>> location;

    private static Collection<Feedback> feedbackListeners = new ArrayList<Feedback>();


    public MakeBoardController(Board board) {
        super(board);
    }


    @Override
    public void update(float dt) {

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
        System.out.println("Sidemargin: " + board.getSidemargin());
        x_pos = x_pos -board.getSidemargin();
        y_pos = y_pos -board.getSidemargin();


        ArrayList<Integer>  indexes = new ArrayList<>();
        System.out.println("Width: " + board.getWidth());
        float t_width = board.getWidth();
        //float t_height = board.getTexture().getHeight();
        float cell_width = t_width / board.getBoard().size();
        float cell_height = t_width / board.getBoard().size();

        indexes.add((int) (x_pos / cell_width));
        indexes.add((int) (y_pos / cell_height));
        System.out.println("Indexes: " +indexes);
        return indexes;
    }

    /**
     * finds the ship located at a specific index and changes the attribute @markedShip
     * finds a new ship if @markedship is null
     * @param indexes   the indexes of the cell you want to find a ship
     */

    public void findShip(ArrayList<Integer> indexes){
        if(markedShip == null ){
            this.markedShip = board.findShip(indexes);
        }
        System.out.println("marked ship is updated");
        board.printShipsLocations();
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
        board.drawShipSquare(this.markedShip);
    }

    /**
     * draws both the board and the ships
     */
    public void drawBoardandShips() {
        board.drawBoard();
        board.drawShips();
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
        System.out.println("indexes to new positions, (live) : " + index);
        //removes the ship from the board (changes the 1-value into 0)
        board.removeShipPosition(markedShip.getLocation());
        //adds the new position to the board (replaces the 0-values on the cells with 1)
        markedShip.createNewPosition(index.get(0), index.get(1));
        // checks if the new location of the markedShip is inside the board
        if(!board.isInsideBoard(markedShip.getLocation())){
            firefeedbackFalse();
            markedShip.createNewPosition(first_position.get(0),first_position.get(1));
        }
        // checks if the ship can be moved to the new location
        else if(!board.isValidLocation(markedShip.getLocation())){
            firefeedbackFalse();
            System.out.println("this is not a valid position");
            markedShip.createNewPosition(first_position.get(0),first_position.get(1));
        }

        else {
            firefeedbackTrue();
        }

        board.addShipPosition(markedShip.getLocation());
        //sets the markedShip value to null so we can select new ships to move :D
        markedShip = null;
        System.out.println("updated board: ");
        board.printBoard();
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
