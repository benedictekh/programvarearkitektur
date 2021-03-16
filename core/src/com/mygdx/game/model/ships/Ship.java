package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.model.Board;

import java.util.ArrayList;
import java.util.List;

public interface Ship {

    public int getSize();
    public boolean isSunk();
    public Texture getTexture();
    public ArrayList<List<Integer>> getLocation();
    public boolean boardChange(Integer xPos, Integer yPos);

}
