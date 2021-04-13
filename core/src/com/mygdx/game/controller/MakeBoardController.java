package com.mygdx.game.controller;

import com.mygdx.game.model.Board;
import com.mygdx.game.model.ships.Ship;

import java.util.ArrayList;

public class MakeBoardController extends Controller{

    Ship markedShip = null;


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
        this.markedShip = board.finShip(indexes);
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



}
