package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.util.ArrayList;

public class Stage {


    Images images = new Images();
    String blockImage = images.getImages();
    String playerImage = images.getImages();
    String enemyImage = images.getImages();
    String lavaEnemy = images.lavaImage();
    String death = images.deathImage();
    Texture deathScreen;
    Player player;
    ArrayList<Platform> platformList = new ArrayList<>();
    ArrayList<Enemy> enemiesList = new ArrayList<>();
    ArrayList<CollidableObject> playerCollidables = new ArrayList<>();

    private Music rainMusic;
    Texture background;
    private OrthographicCamera camera;
    final int BLOCK_SIZE = 50;

    public Stage() {
        deathScreen = new Texture(death);


        platformHolder();
        playerHolder();
        enemyHolder1();
        enemyHolder2();
        lava();






        playerCollidables.addAll(platformList);
        playerCollidables.addAll(enemiesList);

        rainMusic = Gdx.audio.newMusic(Gdx.files.internal("The Boondocks - Uncle Ruckus.mp3"));
        background = new Texture(images.getImages());
        // start the playback of the background music immediately
        rainMusic.setLooping(true);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        rainMusic.play();
    }

    float cameraX;
    float cameraY;

    public void update() {

        if (player.hitbox.y < 0) {
            player.isActive = false;
        }
        cameraX = player.hitbox.x;
        if (cameraX < 400) {
            cameraX = 400;
        }
        cameraY = player.hitbox.y;
        if (cameraY < 240) {
            cameraY = 240;
        }
        camera.position.set(cameraX, cameraY, 0);
        camera.update();
        for (Platform platform : platformList) {
            platform.update();
        }
        for (Enemy enemy : enemiesList) {
            enemy.update(platformList);
        }
        if (player.isActive) {
            player.update(playerCollidables);
        }
        for (int i = 0; i < enemiesList.size(); i++) {
            if (!enemiesList.get(i).isActive) {
                playerCollidables.remove(enemiesList.get(i));
                enemiesList.remove(enemiesList.get(i));
                i--;
            }
        }


    }

    public void draw(SpriteBatch batch) {
        double playerX = player.hitbox.x;
        double playerY = player.hitbox.y;
        batch.begin();
//        if (!player.isActive){
//            for (Images images : imageList){
//
//            }
//        }
        batch.setProjectionMatrix(camera.combined);
        batch.draw(background, 0, 0, 1600, 960);

        for (Platform platform : platformList) {
            platform.draw(batch);
        }
        for (Enemy enemy : enemiesList) {
            enemy.draw(batch);
        }
        if (player.isActive) {
            player.draw(batch);
        } else {
            if (playerX < 400) {
                playerX = 400;
            }
            playerY = 200;
            batch.draw(deathScreen, (float) (playerX - 400), (float) (playerY - 240 + 25), 800, 480 + 50);
            player.dispose();
        }
        batch.end();
    }

    public void dispose() {
        for (Platform platform : platformList) {
            platform.dispose();
        }
        player.dispose();
    }

    public void platformHolder() {
        platformList.add(new Platform(blockImage, 0 * BLOCK_SIZE, 3 * BLOCK_SIZE, 25 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 16 * BLOCK_SIZE, 9 * BLOCK_SIZE, 8 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 16 * BLOCK_SIZE, 6 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 27 * BLOCK_SIZE, 10 * BLOCK_SIZE, 1 * BLOCK_SIZE, 3 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 30 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 32 * BLOCK_SIZE, 10 * BLOCK_SIZE, 17 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 33 * BLOCK_SIZE, 3 * BLOCK_SIZE, 2 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 35 * BLOCK_SIZE, 13 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 36 * BLOCK_SIZE, 6 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 40 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 40 * BLOCK_SIZE, 15 * BLOCK_SIZE, 2 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 44 * BLOCK_SIZE, 13 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 42 * BLOCK_SIZE, 5 * BLOCK_SIZE, 2 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 46 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 48 * BLOCK_SIZE, 5 * BLOCK_SIZE, 1 * BLOCK_SIZE, 3 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 50 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 53 * BLOCK_SIZE, 5 * BLOCK_SIZE, 11 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 56 * BLOCK_SIZE, 8 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 60 * BLOCK_SIZE, 7 * BLOCK_SIZE, 1 * BLOCK_SIZE, 4 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 60 * BLOCK_SIZE, 12 * BLOCK_SIZE, 1 * BLOCK_SIZE, 3 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 68 * BLOCK_SIZE, 7 * BLOCK_SIZE, 4 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 69 * BLOCK_SIZE, 4 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 69 * BLOCK_SIZE, 10 * BLOCK_SIZE, 2 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 74 * BLOCK_SIZE, 3 * BLOCK_SIZE, 2 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 76 * BLOCK_SIZE, 6 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 79 * BLOCK_SIZE, 3 * BLOCK_SIZE, 2 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 81 * BLOCK_SIZE, 5 * BLOCK_SIZE, 1 * BLOCK_SIZE, 3 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 82 * BLOCK_SIZE, 10 * BLOCK_SIZE, 4 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 83 * BLOCK_SIZE, 4 * BLOCK_SIZE, 2 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 86 * BLOCK_SIZE, 6 * BLOCK_SIZE, 4 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 89 * BLOCK_SIZE, 10 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 90 * BLOCK_SIZE, 14 * BLOCK_SIZE, 13 * BLOCK_SIZE, 2 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 92 * BLOCK_SIZE, 10 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 93 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 93 * BLOCK_SIZE, 7 * BLOCK_SIZE, 2 * BLOCK_SIZE, 2 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 96 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 96 * BLOCK_SIZE, 5 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 96 * BLOCK_SIZE, 9 * BLOCK_SIZE, 4 * BLOCK_SIZE, 3 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 98 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 100 * BLOCK_SIZE, 7 * BLOCK_SIZE, 2 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 102 * BLOCK_SIZE, 4 * BLOCK_SIZE, 2 * BLOCK_SIZE, 2 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 102 * BLOCK_SIZE, 10 * BLOCK_SIZE, 2 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 104 * BLOCK_SIZE, 12 * BLOCK_SIZE, 1 * BLOCK_SIZE, 2 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 105 * BLOCK_SIZE, 6 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 106 * BLOCK_SIZE, 3 * BLOCK_SIZE, 4 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 106 * BLOCK_SIZE, 8 * BLOCK_SIZE, 1 * BLOCK_SIZE, 5 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 108 * BLOCK_SIZE, 10 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 110 * BLOCK_SIZE, 7 * BLOCK_SIZE, 5 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 111 * BLOCK_SIZE, 12 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 112 * BLOCK_SIZE, 9 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 115 * BLOCK_SIZE, 3 * BLOCK_SIZE, 4 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 111 * BLOCK_SIZE, 9 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 111 * BLOCK_SIZE, 14 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 121 * BLOCK_SIZE, 4 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 123 * BLOCK_SIZE, 7 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 123 * BLOCK_SIZE, 9 * BLOCK_SIZE, 2 * BLOCK_SIZE, 4 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 124 * BLOCK_SIZE, 3 * BLOCK_SIZE, 2 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 124 * BLOCK_SIZE, 15 * BLOCK_SIZE, 1 * BLOCK_SIZE, 2 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 126 * BLOCK_SIZE, 10 * BLOCK_SIZE, 1 * BLOCK_SIZE, 2 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 127 * BLOCK_SIZE, 14 * BLOCK_SIZE, 4 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 128 * BLOCK_SIZE, 5 * BLOCK_SIZE, 2 * BLOCK_SIZE, 4 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 129 * BLOCK_SIZE, 10 * BLOCK_SIZE, 3 * BLOCK_SIZE, 2 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 132 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 133 * BLOCK_SIZE, 6 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 134 * BLOCK_SIZE, 9 * BLOCK_SIZE, 2 * BLOCK_SIZE, 5 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 138 * BLOCK_SIZE, 3 * BLOCK_SIZE, 14 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 138 * BLOCK_SIZE, 7 * BLOCK_SIZE, 1 * BLOCK_SIZE, 3 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 138 * BLOCK_SIZE, 11 * BLOCK_SIZE, 3 * BLOCK_SIZE, 2 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 143 * BLOCK_SIZE, 6 * BLOCK_SIZE, 3 * BLOCK_SIZE, 5 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 144 * BLOCK_SIZE, 13 * BLOCK_SIZE, 1 * BLOCK_SIZE, 2 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 149 * BLOCK_SIZE, 11 * BLOCK_SIZE, 2 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 149 * BLOCK_SIZE, 15 * BLOCK_SIZE, 3 * BLOCK_SIZE, 4 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 150 * BLOCK_SIZE, 7 * BLOCK_SIZE, 3 * BLOCK_SIZE, 1 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 153 * BLOCK_SIZE, 12 * BLOCK_SIZE, 3 * BLOCK_SIZE, 3 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 154 * BLOCK_SIZE, 8 * BLOCK_SIZE, 1 * BLOCK_SIZE, 3 * BLOCK_SIZE));
        platformList.add(new Platform(blockImage, 156 * BLOCK_SIZE, 3 * BLOCK_SIZE, 27 * BLOCK_SIZE, 1 * BLOCK_SIZE));
    }

    public void enemyHolder1 () {
        enemiesList.add(new KillableEnemy(enemyImage, 6 * BLOCK_SIZE, 4 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE, 3f));
     //   enemiesList.add(new Enemy(enemyImage, 2 * BLOCK_SIZE, 4 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE, 3f));
  //      enemiesList.add(new Enemy(enemyImage, 8 * BLOCK_SIZE, 4 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE, -3f));
    }

    public void enemyHolder2 () {
        enemiesList.add(new Enemy3(enemyImage, 6 * BLOCK_SIZE, 6 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE, 2f, 6*BLOCK_SIZE, 5));
        enemiesList.add(new Enemy3(enemyImage, 2 * BLOCK_SIZE, 6 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE, 3f,16*BLOCK_SIZE, 7));
     //   enemiesList.add(new Enemy3(enemyImage, 8 * BLOCK_SIZE, 6 * BLOCK_SIZE, 1 * BLOCK_SIZE, 1 * BLOCK_SIZE, -2f,4*BLOCK_SIZE));

    }

    public void lava () {
        enemiesList.add(new Enemy(lavaEnemy, 0 * BLOCK_SIZE, 0 * BLOCK_SIZE, 10 * BLOCK_SIZE, 3 * BLOCK_SIZE, 0));
        enemiesList.add(new Enemy(lavaEnemy, 10 * BLOCK_SIZE, 0 * BLOCK_SIZE, 10 * BLOCK_SIZE, 3 * BLOCK_SIZE, 0));
        enemiesList.add(new Enemy(lavaEnemy, 20 * BLOCK_SIZE, 0 * BLOCK_SIZE, 10 * BLOCK_SIZE, 3 * BLOCK_SIZE, 0));
        enemiesList.add(new Enemy(lavaEnemy, 30 * BLOCK_SIZE, 0 * BLOCK_SIZE, 10 * BLOCK_SIZE, 3 * BLOCK_SIZE, 0));
        enemiesList.add(new Enemy(lavaEnemy, 40 * BLOCK_SIZE, 0 * BLOCK_SIZE, 10 * BLOCK_SIZE, 3 * BLOCK_SIZE, 0));
        enemiesList.add(new Enemy(lavaEnemy, 50 * BLOCK_SIZE, 0 * BLOCK_SIZE, 10 * BLOCK_SIZE, 3 * BLOCK_SIZE, 0));
        enemiesList.add(new Enemy(lavaEnemy, 60 * BLOCK_SIZE, 0 * BLOCK_SIZE, 10 * BLOCK_SIZE, 3 * BLOCK_SIZE, 0));
        enemiesList.add(new Enemy(lavaEnemy, 70 * BLOCK_SIZE, 0 * BLOCK_SIZE, 10 * BLOCK_SIZE, 3 * BLOCK_SIZE, 0));

    }
    public void playerHolder (){
        player = new Player(playerImage);
    }


}