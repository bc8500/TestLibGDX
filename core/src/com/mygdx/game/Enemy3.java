package com.mygdx.game;

import java.util.ArrayList;

public class Enemy3 extends Enemy2{
    float yVelocity;
     float jumpHeight = 200 / 40f;
    final float GRAVITY = 5 / 30f;

    public Enemy3(String path, int x, int y, int width, int height, float speed,float flyingDistance,float jumpHeight) {
        super(path,x,y,width,height,speed,flyingDistance);

        distanceFlown = 0;
        this.jumpHeight = jumpHeight;
        yVelocity = jumpHeight;

    }
    public void update(ArrayList<Platform> platforms) {

        move(speed,yVelocity);

        distanceFlown += Math.abs(speed);
        if (distanceFlown >= flyingDistance){
            distanceFlown = 0;
            speed *= -1;
        }else {
            for (int i = 0; i < platforms.size(); i++) {
                if (hitbox.overlaps(platforms.get(i).hitbox)) {
                    move(-speed,0);
                    speed *= -1;
                    distanceFlown=0;
                }

            }
        }
        yVelocity -= GRAVITY;
        if (yVelocity < -1* jumpHeight){
            yVelocity = jumpHeight;
        }


    }
}