package com.mygdx.game;

import java.util.ArrayList;

public class Enemy2 extends KillableEnemy {
    float flyingDistance;
    float distanceFlown;
    public Enemy2(String path, int x, int y, int width, int height, float speed,float flyingDistance) {
        super(path,x,y,width,height,speed);
        this.flyingDistance = flyingDistance;
        distanceFlown = 0;

    }
    public void update(ArrayList<Platform> platforms) {
        move(speed,0);
        distanceFlown += Math.abs(speed);
        if (distanceFlown>= flyingDistance){
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


    }










}
