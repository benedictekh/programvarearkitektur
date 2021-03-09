package com.mygdx.game.model.ships;

import com.badlogic.gdx.graphics.Texture;
import com.mygdx.game.model.Board;

public interface Ship {

    public int getSize();
    public boolean isSunk();
    public Texture getTexture();
    public int getCoordinates();
    public void boardChange(Integer xPos, Integer yPos);

}
