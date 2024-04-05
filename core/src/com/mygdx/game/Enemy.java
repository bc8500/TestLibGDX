package com.mygdx.game;

public class Enemy extends CollidableObject{
    public Enemy(String path, int x, int y, int width, int height) {
        super(path, x, y, width, height);
    }

    @Override
    public void update() {


    }
    public void takeDamage(){
health-=3;
    }
}
