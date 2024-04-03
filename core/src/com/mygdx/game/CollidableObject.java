package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.awt.*;

public class CollidableObject {
    Rectangle hitbox;

    Texture img;

    public CollidableObject(String path, int x, int y, int width, int height) {
        this.img = new Texture(path);
        hitbox = new Rectangle(x,y,width,height);

    }

    public void update(){
    }
    public void draw(SpriteBatch batch){

        batch.draw(img,hitbox.x,hitbox.y,hitbox.width,hitbox.height);
    }

    public void dispose(){
        img.dispose();
    }
}
