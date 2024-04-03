package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;
public class Game extends ApplicationAdapter {
	SpriteBatch batch;
	Stage stage;
	@Override
	public void create () {
		stage = new Stage();
		batch = new SpriteBatch();

	}
	@Override
	public void render () {
		update();
		draw();
	}
	public void update(){
		stage.update();
	}
	public void draw(){
		ScreenUtils.clear(1, 0, 0, 1);
		batch.begin();
//		batch.draw(img, 0, 0);
		stage.draw(batch);
		batch.end();
	}
	@Override
	public void dispose () {
		batch.dispose();
		stage.dispose();
	}
}
