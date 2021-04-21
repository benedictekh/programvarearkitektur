package com.mygdx.game.model;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.mygdx.game.Battleships;
import com.mygdx.game.model.ships.BattleShip;
import com.mygdx.game.model.ships.CarrierShip;
import com.mygdx.game.model.ships.CruiserShip;
import com.mygdx.game.model.ships.DestroyerShip;
import com.mygdx.game.model.ships.PatrolShip;
import com.mygdx.game.model.ships.Ship;
import com.mygdx.game.model.ships.SubmarineShip;

import java.util.ArrayList;
import java.util.List;

public class Board {

    //private Collection<Ship> boardListeners;

    //lage en liste der et ship er representert med et tall, som bare sendes igjennom i starten

    private int sidemargin;

    private ArrayList<List<Integer>> board;
    private ArrayList<Ship> ships;
    //a list used to initalize the opponent board with the ships form this user
    private ArrayList<List<Integer>> initializeOpponentBoard;

    private Cell cell;
    private Texture texture; //the board texture
    private ShapeRenderer shapeRenderer;
    private float width; //the width of the board, board is a square -> width = height
    private int linewitdh = 8;




    public void setBoard(ArrayList<List<Integer>> board) {
        this.board = board;
    }


    public void setShips(ArrayList<Ship> ships) {
        this.ships = ships;
    }

    public void addShip(Ship ship){
        ships.add(ship);
    }

    public ArrayList<List<Integer>> getInitializeOpponentBoard() {
        return initializeOpponentBoard;
    }

    public void setInitializeOpponentBoard(ArrayList<List<Integer>> initializeOpponentBoard) {
        this.initializeOpponentBoard = initializeOpponentBoard;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public ShapeRenderer getShapeRenderer() {
        return shapeRenderer;
    }

    public void setShapeRenderer(ShapeRenderer shapeRenderer) {
        this.shapeRenderer = shapeRenderer;
    }


    public void setWidth(float width) {
        this.width = width;
    }



    public ArrayList<List<Integer>> getOpponentBoard() {
        return initializeOpponentBoard;
    }


    public void setSidemargin(int sidemargin) {
        this.sidemargin = sidemargin;
    }

    public void createNewShipsList(){
        ships = new ArrayList<Ship>();
    }

    public void createNewBoardList(){
        board = new ArrayList<List<Integer>>();
    }

    public void createNewInitializeOpponentBoardList(){
        initializeOpponentBoard = new ArrayList<List<Integer>>();
    }



    /**
     * @return  the list that keeps track of the values on every cell on the board
     */
    public ArrayList<List<Integer>> getBoard(){
        return board;
    }

    /**
     *
     * @return the distance the board should have from the endge of the device when its drawn
     */
    public int getSidemargin(){
        return sidemargin;
    }


    /**
     *
     * @return the width the board should have when its drawn
     */
    public float getWidth(){
        return width;
    }

    /**
     *
     * @return every ship that is placed on the board
     */
    public ArrayList<Ship> getShips() { return  ships;}

    /**
     *
     * @param x x-postion for the cell
     * @param y y-position for the cell
     * @return  returns the value of the cell on a given coordinate
     */
    public int getCellValue(int x, int y){
        return board.get(x).get(y);
    }
}




    /**
     * draws the ships on the board
     * every ships has its own color and every cell the ship occupies is drawn with a circle
     * if the ship is sunk, the drawn circles will be filled
     */
    public void drawShips(){

        float cell_width = width/ getBoard().size();
        for (Ship ship: ships){
            if (ship.isSunk()){
                // draws filled circles
                for ( List<Integer> coordinate : ship.getLocation()) {
                    float x = (coordinate.get(0) * cell_width) + sidemargin + cell_width/2;
                    float y = width - cell_width - (coordinate.get(1) * cell_width) + sidemargin + cell_width/2;
                    Gdx.gl.glLineWidth(100); 
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(ship.getColor());
                    //shapeRenderer.circle(x, y, (cell_width / 2 - 2));
                    for(float i = 0; i<linewitdh; i++){
                       shapeRenderer.circle(x, y, (cell_width / 2 - 8)+i);
                    }

                    shapeRenderer.end();
                }
            }
            else {
                for (List<Integer> coordinate : ship.getLocation()) {
                    float x = (coordinate.get(0) * cell_width) + sidemargin + cell_width / 2;
                    float y = width - cell_width - (coordinate.get(1) * cell_width) + sidemargin + cell_width / 2;
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(ship.getColor());
                    //shapeRenderer.circle(x, y, (cell_width / 2 - 2));
                    for(float i = 0; i<linewitdh; i++){
                        shapeRenderer.circle(x, y, (cell_width / 2 - 8)+i);
                    }
                    shapeRenderer.end();
                }
            }
        }
    }
