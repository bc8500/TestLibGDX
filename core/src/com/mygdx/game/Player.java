package com.mygdx.game;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;


public class Player extends CollidableObject{
    double health = 100;
    double damage = 3;


    boolean canJump = true;

    float speed = 5 / 30f;
    float xVelocity = 0;
    float yVelocity = 0;
    final float JUMP_HEIGHT = 200 / 40;
    static float GRAVITY = 5 / 30f;
    final float FRICTION = 3 / 30f;

    public Player(String path) {
        super(path,0, 300, 40, 45);


    }
    public void update(ArrayList<CollidableObject> platforms) {
//System.out.println(yVelocity);
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canJump || Gdx.input.isKeyPressed(Input.Keys.W) && canJump) {
            yVelocity = JUMP_HEIGHT;
            canJump = false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xVelocity -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xVelocity += speed;
        }

        if (Math.abs(xVelocity) > 4) {
            xVelocity = 4 * Math.signum(xVelocity);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            yVelocity -= speed;
        }

        yVelocity -= GRAVITY;
        xVelocity -= FRICTION * Math.signum(xVelocity);

        // System.out.println(xVelocity);
        if (Math.abs(xVelocity) < (speed - FRICTION)) {
            xVelocity = 0;
        }

        for (int i = 0; i < platforms.size(); i++) {
            if (platforms.get(i) instanceof Enemy){
                if(platforms.get(i).hitbox.overlaps(hitbox) ){
                    isActive=false;
                }
            }

        }

        //moveWithCollision(platforms);
        CollisionInfo  collisionInfo = moveWithCollision(platforms, new Vector2(xVelocity, yVelocity));
        if (!(collisionInfo.side == CollisionInfo.Side.NONE || collisionInfo.side == CollisionInfo.Side.BOTTOM)){
            canJump=true;
        }else {
            canJump=false;
        }
        if(collisionInfo.side == CollisionInfo.Side.TOP) {
            canJump = true;
           if (collisionInfo.object instanceof Platform){
               yVelocity = 0;

           }

            if(collisionInfo.object instanceof KillableEnemy){
                collisionInfo.object.dispose();
                yVelocity =0;

            }
            else if (collisionInfo.object instanceof Enemy){
                isActive = false;
            }

        }else {


            if(collisionInfo.side == CollisionInfo.Side.RIGHT|| collisionInfo.side == CollisionInfo.Side.LEFT) {
                xVelocity =0;

            }
            if(collisionInfo.side == CollisionInfo.Side.BOTTOM){
                if (collisionInfo.object instanceof Platform || collisionInfo.object instanceof KillableEnemy) {
                    yVelocity = 0;

                }
            }

        }


    }

    private void moveWithCollision(ArrayList<CollidableObject> collidableObjects) {
        hitbox.x += xVelocity;// * Gdx.graphics.getDeltaTime();
        hitbox.y += yVelocity;// * Gdx.graphics.getDeltaTime();
        for (CollidableObject object : collidableObjects) {

            if (object instanceof Platform) {
                while (hitbox.overlaps(object.hitbox)) {
                    if (!xMovement()) {
                        xVelocity -= FRICTION * 2 * Math.signum(xVelocity);
                    }
                    if (xVelocity > 0) hitbox.x++;
                    if (xVelocity < 0) hitbox.x--;
                    if (yVelocity < 0) hitbox.y--;
                    if (yVelocity > 0) hitbox.y++;
                    hitbox.y++;
                    canJump = true;
                    yVelocity = 0;
                }
            } else if (object instanceof KillableEnemy) {
                KillableEnemy enemy = (KillableEnemy) object;
                enemy.takeDamage();
                ((KillableEnemy) object).takeDamage();
                System.out.println(health);
            }

        }

    }


    public void draw(SpriteBatch batch) {

        batch.draw(img, hitbox.x, hitbox.y, hitbox.width, hitbox.height);
    }

    public void dispose() {
        img.dispose();
    }

    public boolean xMovement() {

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            return true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            return true;
        }

        return false;
    }


}
