package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.controller.ShipController;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Cell;
import com.mygdx.game.model.ships.Ship;

import java.util.List;

public class GameBoardView {

    private ShapeRenderer shapeRenderer = new ShapeRenderer();


    /**
     * draws the board as a "rutenett" with square cells
     */
    public void drawBoard(Board board){
        // finds the size of each rectangle
        // should be square
        // board is square so width = height
        float cell_width = board.getWidth() / board.getBoard().size();

        for ( int i = 0;  i < board.getBoard().size(); i ++){
            float y_coord = board.getSidemargin() + i * cell_width;
            for ( int j = 0; j < board.getBoard().size(); j ++){
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(0,0,0,0); //black
                shapeRenderer.rect(board.getSidemargin() +j * cell_width, y_coord, cell_width, cell_width );
                shapeRenderer.end();
            }
        }
    }

    /**
     * draws the ships on the board
     * every ships has its own color and every cell the ship occupies is drawn with a circle
     * if the ship is sunk, the drawn circles will be filled
     */
    public void drawShips(Board board){
        float cell_width = board.getWidth()/ board.getBoard().size();
        for (Ship ship: board.getShips()){
            if (ship.getSunk()){
                // draws filled circles
                for ( List<Integer> coordinate : ship.getLocation()) {
                    float x = (coordinate.get(0) * cell_width) + board.getSidemargin() + cell_width/2;
                    float y =board.getWidth() - cell_width - (coordinate.get(1) * cell_width) + board.getSidemargin() + cell_width/2;
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(ship.getColor());
                    shapeRenderer.circle(x, y, cell_width / 2 - 2);
                    shapeRenderer.end();
                }
            }
            else {
                for (List<Integer> coordinate : ship.getLocation()) {
                    float x = (coordinate.get(0) * cell_width) + board.getSidemargin() + cell_width / 2;
                    float y = board.getWidth() - cell_width - (coordinate.get(1) * cell_width) + board.getSidemargin() + cell_width / 2;
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(ship.getColor());
                    shapeRenderer.circle(x, y, cell_width / 2 - 2);
                    shapeRenderer.end();
                }
            }
        }
    }

    /**
     * draws the selected ship as a rectangle that shows every cell the ship is occupying
     * @param ship  the ship you want to draw as a rectangle
     */
    public void drawShipSquare(Board board, Ship ship){
        float cell_width = board.getWidth()/ board.getBoard().size();
        for ( List<Integer> coordinate : ship.getLocation()) {
            float x = (coordinate.get(0) * cell_width) + board.getSidemargin();
            float y =board.getWidth() - cell_width - (coordinate.get(1) * cell_width) + board.getSidemargin();
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(ship.getColor());
            shapeRenderer.rect(x, y, cell_width ,cell_width);
            shapeRenderer.end();
        }
    }

    /**
     * draws only the ships that are sunk
     * is used for the opponent board so that you only can see the ships that are fully hit
     */

    public void drawSunkShip(Board board){
        float cell_width = board.getWidth()/ board.getBoard().size();
        for (Ship ship: board.getShips()){
            if (ship.getSunk()){
                // draws filled circles
                for ( List<Integer> coordinate : ship.getLocation()) {
                    float x = (coordinate.get(0) * cell_width) + board.getSidemargin() + cell_width/2;
                    float y = board.getWidth() - cell_width - (coordinate.get(1) * cell_width) + board.getSidemargin() + cell_width/2;
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(ship.getColor());
                    shapeRenderer.circle(x, y, cell_width / 2 - 2);
                    shapeRenderer.end();
                }
            }
        }

    }


    /**
     * draws the different shots that has been done on the different cells on the board
     * if the cell has value HIT it will be drawn with an X
     * if the cell has value MISS it will be drawn with an /
     */
    public void drawUpdatedBoard(Board board){

        float cell_width = board.getWidth()/ board.getBoard().size();
        for ( int i = 0;  i < board.getBoard().size(); i ++){
            float y_coord = i * cell_width;
            for ( int j = 0; j < board.getBoard().size(); j ++){
                if (board.getBoard().get(i).get(j) == Cell.HIT) {
                    // draw a cross
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.BLACK);
                    shapeRenderer.line(board.getSidemargin() +j * cell_width, board.getWidth() + board.getSidemargin() - cell_width - y_coord, board.getSidemargin() +j * cell_width + cell_width, board.getWidth() + board.getSidemargin() - y_coord);
                    shapeRenderer.line(board.getSidemargin() + j*cell_width, board.getWidth() + board.getSidemargin() - y_coord, board.getSidemargin() + j*cell_width + cell_width, board.getWidth() + board.getSidemargin() - y_coord - cell_width);
                    shapeRenderer.end();
                }
                else if (board.getBoard().get(i).get(j) == Cell.MISS) {
                    // draws a diagonal line
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.BLACK);
                    shapeRenderer.line(board.getSidemargin() +j * cell_width, board.getWidth() + board.getSidemargin() - cell_width - y_coord, board.getSidemargin() +j * cell_width + cell_width, board.getWidth() + board.getSidemargin() - y_coord);
                    shapeRenderer.end();
                }
            }

        }

    }

    //skal gjøres fra GameBoardView
    public void drawBoardView(boolean myTurn, Board board){
        if(myTurn){
            drawBoard(board);
            drawSunkShip(board);
            drawUpdatedBoard(board);
        }
        else {
            drawBoard(board);
            drawShips(board);
            drawUpdatedBoard(board);
        }
    }

    /**
     * draws both the board and the ships
     */
    public void drawBoardandShips(Board board) {
        drawBoard(board);
        drawShips(board);
    }

    /**
     * draws the marked ship as a square
     */

    public void drawMarkedShip(Ship ship, Board board) {
        drawShipSquare(board, ship);
    }
}