package com.mygdx.game.view;

import com.mygdx.game.Game;

public class MultiplayerView extends State {

    private int width = Game.WIDTH;
    private int height = Game.HEIGHT;

    private Board board;
    private Player player;
    private Cell cell;
    private DestroyerShip destroyerShip;
    private CarrierShip carrierShip;
    private BattleShip battleShip;
    private CruiserShip cruiserShip;
    private SubmarineShip submarineShip;

    public MultiplayerView(GameStateManager gsm, Board board){
        super(gsm) //vet ikke helt hva dette betyr, må finne ut

        map = new Pixmap(width, height, Pixmap.Format.RGBA8888);
    }



}
