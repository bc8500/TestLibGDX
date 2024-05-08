package com.mygdx.game;

import java.util.ArrayList;

public class Enemy extends CollidableObject{
    double health = 100;
    float speed;
    Rectangle groundDetectorLeft;
    Rectangle groundDetectorRight;
    public Enemy(String path, int x, int y, int width, int height, float speed) {
        super(path, x, y, width, height);
        groundDetectorLeft = new Rectangle(x,y- 10, 1,12);
        groundDetectorRight = new Rectangle(x+width,y- 10, 1,12);
        this.speed=speed;
    }


    public void update(ArrayList<Platform> platforms) {
        move(speed,0);
        for (int i = 0; i < platforms.size(); i++) {
            if (groundDetectorLeft.overlaps(platforms.get(i).hitbox)){
                speed*=-1;
            }if (groundDetectorRight.overlaps(platforms.get(i).hitbox)){
                speed*=-1;
            }
        }


    }
    public void takeDamage(){
        health-=3;
    }

    void move(float x, float y){
        hitbox.x += x;
        hitbox.y +=y;
        groundDetectorLeft.x += x;
        groundDetectorRight.x +=x;
        groundDetectorLeft.y += y;
        groundDetectorRight.y +=y;

    }
}
