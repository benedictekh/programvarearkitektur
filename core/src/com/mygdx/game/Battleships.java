package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.controller.FirebaseConnector;
import com.mygdx.game.model.Board;
import com.mygdx.game.model.Player;
import com.mygdx.game.model.ScoreBoard;
import com.mygdx.game.view.MenuView;
import com.mygdx.game.view.PlayView;
import com.mygdx.game.view.GameStateManager;

import java.nio.file.Watchable;
import java.util.HashMap;

public class Battleships extends ApplicationAdapter {
	private SpriteBatch batch;
	private GameStateManager gsm;
	public static int WIDTH;
	public static int HEIGHT;
	FirebaseServices firebaseServices;
	//public static FirebaseConnector firebaseConnector;
	public static GameCodeHolder firebaseConnector;

	public Battleships(FirebaseServices firebaseServices) {
		this.firebaseServices = firebaseServices;
	}

	@Override
	public void create () {
		WIDTH = Gdx.app.getGraphics().getWidth();
		HEIGHT = Gdx.app.getGraphics().getHeight();

		//this.firebaseConnector = new FirebaseConnector(firebaseServices);
		this.firebaseConnector = GameCodeHolder.getInstance(firebaseServices);
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		gsm.push(new MenuView(gsm));

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
