package com.mygdx.game.controller;

import com.mygdx.game.model.Board;

public abstract class Controller {
    Board board;

    Controller(Board board) {
        this.board = board;
    }


    public abstract void update(float dt);
}
