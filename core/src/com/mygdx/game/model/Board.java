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
    private float width; //the width of the board, board is a square -> width = height


    /**
     * the constructor, sets the sidemargin  and the size of the board
     * sets the width of the board based on the size on the device
     * @param size  the size the board should have, how many cells it should contain in x- and y-direction
     *              the board is a square -> size = 10 would mean a 10x10 board -> 100 cells on the board
     * @param sidemargin    the distance the board should have from the endge of the device when its drawn
     */
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
        //initNewShip();
    }

    /**
     * creates a board with the given size
     * the board is a double-linked-list wit the same amounts of rows and columns
     * where each element in the list is a "cell"
     * every cell is given the value EMPTY (0) when the board is created
     * @param size  the size the board should have, how many cells it should contain in x- and y-direction
     *              the board is a square -> size = 10 would mean a 10x10 board -> 100 cells on the board
     */
    public ArrayList<List<Integer>> getOpponentBoard(){
        return initializeOpponentBoard;
    }

    public Board(ArrayList<List<Integer>> initializeOpponentBoard, int sidemargin){
        shapeRenderer = new ShapeRenderer();
        cell = new Cell();
        this.sidemargin = sidemargin;
        if (Battleships.WIDTH > Battleships.HEIGHT){
            width = Battleships.HEIGHT - (2 * sidemargin);
        }
        else{
            width = Battleships.WIDTH - (2 * sidemargin);
        }
        makeBoard(initializeOpponentBoard.size());
        createOpponentLists(initializeOpponentBoard);
    }

    public void createOpponentLists(ArrayList<List<Integer>> initializeOpponentBoard){
        ships = new ArrayList<Ship>();


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

    /**
     * adds ships to the list of ships and creates a random location for every ship
     * the ships cannot overlap
     * every cell the different ships occupies will get the value SHIP (0) on the board (ArrayList<List<Integer>)
     */

    private void initShips(){
        // init ships from Ship class
        ships.add(new DestroyerShip(true));
        ships.add(new CarrierShip(true));
        ships.add(new CruiserShip(false));
        ships.add(new SubmarineShip(true));
        ships.add(new BattleShip(true));
        ships.add(new PatrolShip(true));
        for (Ship ship : ships){
            ship.createRandomLocation();
        }
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
                updateBoard(x, y, cell.SHIP);
                updateInitalizeOpponentBoard(x, y, ship.getShipNr());
            }
        }
        System.out.println("dette kommer fra initShip");
        printBoard();
    }
    /*
    private void initNewShip() {
        ships.add(new CruiserShip(true));
        for(Ship ship: ships){
            ship.createNewPosition(1,1);

        }
}
     */

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

    /**
     * checks of the coordinates of a move is valid
     * checks both if the coordinates (indexes) is on the board and of the chosen cell is valid to shoot at
     * @param x the x-coordinate for the move
     * @param y the y-coordinate for the move
     * @return  a boolean that tells if the move is valid or not
     *          true if valid, false if not
     */
    private boolean isValidMove(int x, int y){
        if (( 0 <= x && x < 10) && (0 <= y && y < 10) ){
            System.out.println("Valid coordinates");
            // coordinates is within range, check if cell is already been shot at
            int value = board.get(y).get(x);
            return cell.isValidMove(value);
        }
        return false;
    }

    /**
     * checks if the whole position for a ship is inside the board
     * @param shipPosition  the coordinates of the different cells the ship is occupying
     * @return  true if the whole ship is placed inside the board, false if not
     */
    public Boolean isInsideBoard(ArrayList<List<Integer>> shipPosition){
        boolean bool = true;
        System.out.println(shipPosition);
        // iterates trough the index for every cell the ship is occupying
        for(List<Integer> index: shipPosition){
            int x= index.get(0);
            int y = index.get(1);
            // cheks that both values is between 0 and 9
            if(!(( 0 <= x && x < 10) && (0 <= y && y < 10))) {
                bool =  false;
            }
            else {
                System.out.println(index+" er  lov");
            }
        }
        return bool;
    }

    /**
     * shoots on the board at the given coordinates and updates the value on the board if the shot vas valid
     * @param x the x-position for the shot
     * @param y the y-position for the shot
     * @return  false if the the shot vas not valid or a hit, true if both valid and a miss
     *          (returns true if it should be a change of turn after this shot)
     */
    // return true if valid move and miss, false if not valid move or hit
    public boolean shoot(int x, int y){
        // is the cell you are trying to shoot at a valid cell? (on the board and not shot at earlier)
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

    /**
     * updates the cell on the board with the given value
     * @param x x-position for the cell
     * @param y y-position for the cell
     * @param value the value the cell should be updates with
     */

    public void updateBoard(int x, int y, int value){
        List<Integer> tmp = board.get(y);
        tmp.set(x, value);
        board.set(y,tmp);
    }
    public void updateInitalizeOpponentBoard(int x, int y, int value){
        List<Integer> tmp = initializeOpponentBoard.get(y);
        tmp.set(x, value);
        initializeOpponentBoard.set(y,tmp);
    }


    /**
     * checks if a ship can be placed on a given location, or if there already is a ship placed on one of the cells
     * @param location the location for every cell a ship should occupy
     * @return  true if every cell is available, false if there already is a ship on one of the cells
     */
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

    /**
     *
     * @return the boards texture
     */
    public Texture getTexture(){
        return texture;
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
     * @return  returns the value of the cell on a given coordiante
     */
    public int getCellValue(int x, int y){
        return board.get(x).get(y);
    }

    /**
     * finds the ship that is placed on a selected cell
     * @param indexes   the indexes for the selected cell
     * @return  returns the ship that is occupying the cell, null if the cell doesn't have a ship
     */
    public Ship findShip(ArrayList<Integer> indexes){
        for(Ship ship: ships){
            if(ship.getLocation().contains(indexes)){
                System.out.println("ships loactions contains indexes: " + ship.getLocation() + "indexes: " + indexes);
                return ship;
            }
        }
        return null;
    }

    /**
     * draws the board as a "rutenett" with square cells
     */
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

    /**
     * draws the selected ship as a rectangle that shows every cell the ship is occupying
     * @param ship  the ship you want to draw as a rectangle
     */
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


    /**
     * draws the different shots that has been done on the different cells on the board
     * if the cell has value HIT it will be drawn with an X
     * if the cell has value MISS it will be drawn with an /
     */
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

    /**
     * checks if every ship on the board is sunk (the game is over)
     * @return true if every ship is sunk, false otherwise
     */
    public boolean isFinished(){
        for(Ship ship: ships){
            if (!ship.isSunk()){
                return false;
            }
        }
        return true;
    }

    /**
     * changes the value on every cell a ship has earlier occupied to be empty
     * @param location  the location for every cell that should change value
     */
    public void removeShipPosition(ArrayList<List<Integer>> location){
        for (List<Integer> coordinate : location) {
            int x = coordinate.get(0);
            int y = coordinate.get(1);
            updateBoard(x, y, cell.EMPTY);
        }
    }

    /**
     * adds the value of a ship to the cells a ship is occupying
     * @param location  the location for every cell that should change value
     */
    public void addShipPosition(ArrayList<List<Integer>> location) {
        for (List<Integer> coordinate : location) {
            int x = coordinate.get(0);
            int y = coordinate.get(1);
            updateBoard(x, y, cell.SHIP);
        }
    }

}

