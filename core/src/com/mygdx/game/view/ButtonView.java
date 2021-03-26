package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;

import java.awt.Rectangle;

public class ButtonView {


    private Texture texture;
    public int Buttonx;
    public int Buttony;
    public int Width;
    public int Height;

    private Rectangle rectangle;



    public ButtonView(String texturePath, int buttonx, int buttony, int width, int height) {
        texture = new Texture(texturePath);
        Buttonx= buttonx;
        Buttony = buttony;
        Width = width;
        Height = height;
        rectangle = new Rectangle(Width,Height);
        rectangle.setLocation(buttonx,buttony);
    }

    public Rectangle getRectangle() {
        return rectangle;
    }
    public Texture getTexture(){
        return texture;
    }
}
