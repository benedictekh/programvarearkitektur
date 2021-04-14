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

    private Collection<Feedback> feebackListeners = new ArrayList<Feedback>();


    public MakeBoardController(Board board) {
        super(board);
    }


    @Override
    public void update(float dt) {

    }

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

    public void findShip(ArrayList<Integer> indexes){
        if(markedShip == null ){
            this.markedShip = board.findShip(indexes);
        }
        System.out.println("marked ship is updated");
        board.printShipsLocations();
    }

    public Ship getMarkedShip() {
        return this.markedShip;
    }

    public void drawMarkedShip() {
        board.drawShipSquare(this.markedShip);
    }

    public void drawBoardandShips() {
        board.drawBoard();
        board.drawShips();
    }

    public void moveShip(int new_x, int new_y, ArrayList<List<Integer>> location) {
        ArrayList<Integer> index= getIndex(new_x,new_y);
        List<Integer> first_position = markedShip.getLocation().get(0);
        System.out.println("indexes to new positions, (live) : " + index);

        board.removeShipPosition(markedShip.getLocation());
        markedShip.createNewPosition(index.get(0), index.get(1));
        if(!board.isInsideBoard(markedShip.getLocation())){
            firefeedhackFlase();
            markedShip.createNewPosition(first_position.get(0),first_position.get(1));
        }
        else if(!board.isValidLocation(markedShip.getLocation())){
            firefeedhackFlase();
            System.out.println("this is not a valid position");
            markedShip.createNewPosition(first_position.get(0),first_position.get(1));
        }
        else {
            firefeedhackTrue();
        }
        board.addShipPosition(markedShip.getLocation());
        markedShip = null;
        System.out.println("updated board: ");
        board.printBoard();
    }

    public void addFeedbackListener(Feedback feedbackListener) {
        feebackListeners.add(feedbackListener);
    }

    public void removeCrashListener(Feedback feedbackListener) {
        feebackListeners.remove(feedbackListener);
    }

    public void fireAction(Feedback crashListener) {

    }

    public void firefeedhackTrue() {
        for (Feedback feedbackListener: feebackListeners) {
            feedbackListener.fireAction(true);
        }
    }
    public void firefeedhackFlase() {
        for (Feedback feedbackListener: feebackListeners) {
            feedbackListener.fireAction(false);
        }
    }

}
