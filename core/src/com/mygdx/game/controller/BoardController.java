package com.mygdx.game.controller;

import com.mygdx.game.model.Board;

import java.util.ArrayList;

public class BoardController extends Controller{

    public BoardController(Board board) {
        super(board);
    }

    @Override
    public void update(float dt) {

    }

    public ArrayList<Integer> getIndex(float x_pos, float y_pos){
        //finds the position on the board
        x_pos = x_pos -board.getSidemargin();
        y_pos = y_pos -board.getSidemargin();


        ArrayList<Integer>  indexes = new ArrayList<>();
       float t_width = board.getWidth();
       //float t_height = board.getTexture().getHeight();
       float cell_width = t_width / board.getBoard().size();
       float cell_height = t_width / board.getBoard().size();


       indexes.add((int) (x_pos / cell_width));
       indexes.add((int) (y_pos / cell_height));
       System.out.println(indexes);
       return indexes;
    }

    public Board getBoard(){
        return this.board;
    }
}
