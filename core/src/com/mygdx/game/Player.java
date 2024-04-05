package com.mygdx.game;


import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;


public class Player {
    double health = 100;
    double damage = 3;
    Rectangle hitbox;
    Texture img;

    boolean canJump = true;

    float speed = 5 / 30f;
    float xVelocity = 0;
    float yVelocity = 0;
    final float JUMP_HEIGHT = 200 / 40;
    final float GRAVITY = 5 / 30f;
    final float FRICTION = 3 / 30f;

    public Player(String path) {
        this.img = new Texture(path);
        hitbox = new Rectangle(0, 100, 50, 50);

    }

    public void update(ArrayList<CollidableObject> platforms) {

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            xVelocity -= speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            xVelocity += speed;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canJump) {
            yVelocity = JUMP_HEIGHT;
            canJump = false;
        }

        if (Math.abs(xVelocity) > 4) {
            xVelocity = 4 * Math.signum(xVelocity);
        }


        yVelocity -= GRAVITY;
        xVelocity -= FRICTION * Math.signum(xVelocity);

        // System.out.println(xVelocity);
        if (Math.abs(xVelocity) < (speed - FRICTION)) {
            xVelocity = 0;
        }
        moveWithCollision(platforms);


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
                    //if (xVelocity > 0) hitbox.x++;
                    //if (xV
                    // elocity < 0) hitbox.x--;
                    //if (yVelocity < 0) hitbox.y--;
                    hitbox.y++;
                    canJump = true;
                    yVelocity = 0;
                }
            } else if (object instanceof Enemy) {
                Enemy enemy = (Enemy) object;
                enemy.takeDamage();
                ((Enemy) object).takeDamage();
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
