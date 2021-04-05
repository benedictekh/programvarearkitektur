package com.mygdx.game.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;



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
        rectangle = new Rectangle(Buttonx,Buttony,Width,Height);
        //rectangle.setLocation();
        System.out.println("buttonx" + Buttonx);
    }

    public Rectangle getRectangle() {
        System.out.println("rec:" + rectangle);
        return this.rectangle;

    }
    public Texture getTexture(){
        return texture;
    }
}
