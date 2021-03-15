package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.game.model.Board;
import com.mygdx.game.view.PlayView;
import com.mygdx.game.view.GameStateManager;

public class battleships extends ApplicationAdapter {
	private SpriteBatch batch;
	private Texture img;
	private GameStateManager gsm;
	public static int WIDTH;
	public static int HEIGHT;
	private PlayView playView;
	private Board board;
	private Texture picture;

	@Override
	public void create () {
		WIDTH = Gdx.app.getGraphics().getWidth();
		HEIGHT = Gdx.app.getGraphics().getHeight();

		batch = new SpriteBatch();
		picture =  new Texture("ocean.jpeg");
		gsm = new GameStateManager();
		playView = new PlayView(gsm,board);
		//Gdx.gl.glClearColor(1, 0, 0, 1);

	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(1, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);
		/*
		batch.begin();
		batch.draw(picture,0,0);
		batch.end();
		 */
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
