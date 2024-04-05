package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;
import java.util.ArrayList;
import java.util.Vector;

public class Stage {

    Player player;
    ArrayList<Platform> platformList =new ArrayList<>();
    ArrayList<Enemy> enemiesList =new ArrayList<>();
    ArrayList<CollidableObject> playerCollidables = new ArrayList<>();

    private Music rainMusic;
    Texture marioBackground;
    private OrthographicCamera camera;

    public Stage() {
        player=new Player("masterchieftpose.jfif");
        platformList.add(new Platform("badlogic.jpg",-400,0,1600,20));
        platformList.add(new Platform("masterchieftpose.jfif", 40, 40,400,20));

        enemiesList.add(new Enemy("badlogic.jpg" , 300,60,40,40));

        playerCollidables.addAll(platformList);
        playerCollidables.addAll(enemiesList);

        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("rain.mp3"));
        marioBackground = new Texture("marioBackground.png");
        // start the playback of the background music immediately
        rainMusic.setLooping(true);
        camera = new OrthographicCamera();
        camera.setToOrtho(true, 800, 480);
        rainMusic.play();
    }
 
    public void update(){
        for (Platform platform:platformList ) {
            platform.update();
        }
        for (Enemy enemy:enemiesList ) {
            enemy.update();
        }
        player.update(playerCollidables);


    }
    public void draw(SpriteBatch batch){
        batch.draw(marioBackground, 0, 0, 1600, 960);

        for (Platform platform:platformList ) {
            platform.draw(batch);
        }
            for (Enemy enemy:enemiesList ) {
            enemy.draw(batch);
        }
        player.draw(batch);
    }

    public void dispose(){
        for (Platform platform:platformList ) {
            platform.dispose();
        }
        player.dispose();
    }

}
