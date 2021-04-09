package com.mygdx.game.model;

public class MakeBoard {

    Board board;

    public MakeBoard(){
        board = new Board(10,10);
    }

    public Board getBoard(){
        return board;
    }
}
