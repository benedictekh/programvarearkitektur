package com.mygdx.game.model;

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
    private float width; //board is a square -> width = height


    public Board(int size, int sidemargin){
        this.sidemargin = sidemargin;
        shapeRenderer = new ShapeRenderer();
        cell = new Cell();
        ships = new ArrayList<>();
        if (Battleships.WIDTH > Battleships.HEIGHT){
            width = Battleships.HEIGHT - (2 * sidemargin);
        }
        else{
            width = Battleships.WIDTH - (2 * sidemargin);
        }
        makeBoard(size);
        System.out.println("Nytt brett kommer nå \n");
        initShips();
    }

    public ArrayList<List<Integer>> getOpponentBoard(){
        return initializeOpponentBoard;
    }

    public Board(ArrayList<List<Integer>> initializeOpponentBoard){
        makeBoard(initializeOpponentBoard.size());
        createOpponentLists(initializeOpponentBoard);
    }

    public void createOpponentLists(ArrayList<List<Integer>> initializeOpponentBoard){
        ships.add(new DestroyerShip(true));
        ships.add(new CarrierShip(true));
        ships.add(new CruiserShip(false));
        ships.add(new SubmarineShip(true));
        ships.add(new BattleShip(true));
        ships.add(new PatrolShip(true));
        for(int row = 0; row < initializeOpponentBoard.size(); row++){
            for(int col = 0; col < initializeOpponentBoard.size(); col++){
                if(initializeOpponentBoard.get(row).get(col) < 0){
                    updateBoard(row, col, cell.SHIP);
                    for(Ship ship : ships){
                        if(initializeOpponentBoard.get(row).get(col) == ship.getShipNr()){
                            ship.addLocation(row, col);
                            break;
                        }
                    }
                }
            }
        }
        System.out.println("Board laget av createOpponentLists"+ board);
        System.out.println("Ships laget av createOpponentLists"+ ships);

    }


    private void makeBoard(int size) {
        board = new ArrayList<List<Integer>>();
        initializeOpponentBoard = new ArrayList<List<Integer>>();

        for (int row = 0; row < size; row++) {
            List<Integer> kolonne = new ArrayList<>();
            List<Integer> kolonne2 = new ArrayList<>();

            for (int column = 0; column < size; column++) {
                kolonne.add(cell.EMPTY);
                kolonne2.add(cell.EMPTY);

            }
            board.add(kolonne);
            initializeOpponentBoard.add(kolonne2);
        }
        printBoard();
    }

// legger til skip med random plassering, while løkke ikke overlapper emd skip
    private void initShips(){
        // init ships from Ship class
        ships.add(new DestroyerShip(true));
        ships.add(new CarrierShip(true));
        ships.add(new CruiserShip(false));
        ships.add(new SubmarineShip(true));
        ships.add(new BattleShip(true));
        ships.add(new PatrolShip(true));
        for (Ship ship: ships){

            System.out.println("location: " + ship.getLocation());
            // while the ships random location is partly occupied, create a new random location
            while (!isValidLocation(ship.getLocation())) {
                ship.createRandomLocation();
            }
            // the location is valid, update the values on the board
            for (List<Integer> coordinate : ship.getLocation()) {
                int x = coordinate.get(0);
                int y = coordinate.get(1);
                System.out.println("Kan du vennligst printe dette");
                updateBoard(x, y, cell.SHIP);
                updateInitalizeOpponentBoard(x, y, ship.getShipNr());
            }
        }
        System.out.println("dette kommer fra initShip");
        printBoard();
    }


    private void printBoard(){
        System.out.println("Dette er board");
        for (int row = 0; row < board.size(); row ++){
            System.out.println(board.get(row) + "\n");
        }
        System.out.println("Dette er initializeOpponentBoard");
        for (int row = 0; row < initializeOpponentBoard.size(); row ++){
            System.out.println(initializeOpponentBoard.get(row) + "\n");
        }
    }


    private boolean isValidMove(int x, int y){
        if (( 0 <= x && x < 10) && (0 <= y && y < 10) ){
            System.out.println("Valid coordinates");
            // coordinates is within range, check if cell is already been shot at
            int value = board.get(y).get(x);
            return cell.isValidMove(value);
        }
        return false;
    }

    // return true if valid move and miss, false if not valid move or hit
    public boolean shoot(int x, int y){
       if (isValidMove(x, y)){
           int value = board.get(y).get(x);
           System.out.println("Valid move");
           if (cell.isHit(value)){
               for (Ship ship : ships){
                   ship.boardChange(x, y);

               }
               updateBoard(x, y,cell.setCell(value));
               return false;
           }
           else {
               updateBoard(x, y, cell.setCell(value));
               return true;
           }

       }
       else{
           System.out.println("Not a valid move");
           return false;
       }
    }

    public void updateBoard(int x, int y, int value){
        List<Integer> tmp = board.get(y);
        tmp.set(x, value);
        board.set(y,tmp);
        System.out.println("Board is updated, time to draw");
    }
    public void updateInitalizeOpponentBoard(int x, int y, int value){
        List<Integer> tmp = initializeOpponentBoard.get(y);
        tmp.set(x, value);
        initializeOpponentBoard.set(y,tmp);
        System.out.println("Board is updated, time to draw");
    }

    public boolean isValidLocation(ArrayList<List<Integer>> location){
        for (List<Integer> coordinates : location){
            if (board.get(coordinates.get(1)).get(coordinates.get(0)) == cell.SHIP){
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        Board test = new Board(10, 10);
        /*test.shoot(2,1);
        System.out.println("Skutt på 2, 1");
        test.printBoard();
        System.out.println(test.isValidMove(11, 3)); */

    }

    public Texture getTexture(){
        return texture;
    }

    public ArrayList<List<Integer>> getBoard(){
        return board;
    }

    public int getSidemargin(){
        return sidemargin;
    }

    public float getWidth(){
        return width;
    }

    public ArrayList<Ship> getShips() { return  ships;}

    public int getCellValue(int x, int y){
        return board.get(x).get(y);
    }
    public Ship finShip(ArrayList<Integer> indexes){
        for(Ship ship: ships){
            if(ship.getLocation().contains(indexes)){
                System.out.println("ships loactions contains indexes: " + ship.getLocation() + "indexes: " + indexes);
                return ship;
            }
        }
        return null;

    }
    public void drawBoard(){
        // finds the size of each rectangle
        // should be square
        // board is square so width = height
        float cell_width = width / getBoard().size();

        for ( int i = 0;  i < getBoard().size(); i ++){
            float y_coord = sidemargin + i * cell_width;
            for ( int j = 0; j < getBoard().size(); j ++){
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(0,0,0,0); //black
                shapeRenderer.rect(sidemargin +j * cell_width, y_coord, cell_width, cell_width );
                shapeRenderer.end();
            }

        }

    }

    public void drawShips(){
        float cell_width = width/ getBoard().size();
        for (Ship ship: ships){
            if (ship.isSunk()){
                // draws filled circles
                for ( List<Integer> coordinate : ship.getLocation()) {
                    float x = (coordinate.get(0) * cell_width) + sidemargin + cell_width/2;
                    float y = width - cell_width - (coordinate.get(1) * cell_width) + sidemargin + cell_width/2;
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(ship.getColor());
                    shapeRenderer.circle(x, y, cell_width / 2 - 2);
                    shapeRenderer.end();
                }

            }
            else {
                for (List<Integer> coordinate : ship.getLocation()) {
                    float x = (coordinate.get(0) * cell_width) + sidemargin + cell_width / 2;
                    float y = width - cell_width - (coordinate.get(1) * cell_width) + sidemargin + cell_width / 2;
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(ship.getColor());
                    shapeRenderer.circle(x, y, cell_width / 2 - 2);
                    shapeRenderer.end();
                }
            }
        }
    }
    public void drawShipSquare(Ship ship){
        float cell_width = width/ getBoard().size();
                for ( List<Integer> coordinate : ship.getLocation()) {
                    float x = (coordinate.get(0) * cell_width) + sidemargin;
                    float y = width - cell_width - (coordinate.get(1) * cell_width) + sidemargin;
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
                    shapeRenderer.setColor(ship.getColor());
                    shapeRenderer.rect(x, y, cell_width ,cell_width);
                    shapeRenderer.end();
                }
        }


    public void drawUpdatedBoard(){

        float cell_width = width/ getBoard().size();
        for ( int i = 0;  i < getBoard().size(); i ++){
            float y_coord = i * cell_width;
            for ( int j = 0; j < getBoard().size(); j ++){
                if (board.get(i).get(j) == Cell.HIT) {
                    // draw a cross
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.BLACK);
                    shapeRenderer.line(sidemargin +j * cell_width, width + sidemargin - cell_width - y_coord, sidemargin +j * cell_width + cell_width, width + sidemargin - y_coord);
                    shapeRenderer.line(sidemargin + j*cell_width, width + sidemargin - y_coord, sidemargin + j*cell_width + cell_width, width + sidemargin - y_coord - cell_width);
                    shapeRenderer.end();
                }
                else if (board.get(i).get(j) == Cell.MISS) {
                    // draws a diagonal line
                    shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                    shapeRenderer.setColor(Color.BLACK);
                    shapeRenderer.line(sidemargin +j * cell_width, width + sidemargin - cell_width - y_coord, sidemargin +j * cell_width + cell_width, width + sidemargin - y_coord);
                    shapeRenderer.end();
                }
            }

        }

    }
    public void printShipsLocations() {
        for(Ship ship: ships){
            System.out.println("location for ships: " + ship.getLocation());
        }
    }

    public boolean isFinished(){
        for(Ship ship: ships){
            if (!ship.isSunk()){
                return false;
            }
        }
        return true;
    }


}

