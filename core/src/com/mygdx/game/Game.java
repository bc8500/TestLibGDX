package com.mygdx.game;
import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
		if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)){
			stage = new Stage();
		}
		if (Gdx.input.isKeyJustPressed(Input.Keys.TAB)){
			batch = new SpriteBatch();
		}
		update();
		draw();
	}
	public void update(){
		stage.update();
	}
	public void draw(){
		ScreenUtils.clear(1, 0, 0, 1);


//		batch.draw(img, 0, 0);
		stage.draw(batch);

	}
	@Override
	public void dispose () {
		batch.dispose();
		stage.dispose();
	}
}
