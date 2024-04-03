package com.mygdx.game;

import java.awt.*;
import java.util.ArrayList;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Player {
    Rectangle hitbox;
    Texture img;

    boolean canJump = true;

    float speed = 5/60f;
    float xVelocity =0;
    float yVelocity =0;
    final float JUMP_HEIGHT = 200/60;
    final float GRAVITY = 5/60f;
    final float FRICTION = 3/60f;
    public Player(String path) {
        this.img = new Texture(path);
        hitbox = new Rectangle(0,100,50,50);

    }

    public void update(ArrayList<CollidableObject> platforms){

        if (Gdx.input.isKeyPressed(Input.Keys.A)){
            xVelocity-=speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)){
            xVelocity+=speed;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) && canJump){
            yVelocity = JUMP_HEIGHT;
            canJump = false;
        }

        if(Math.abs(xVelocity) > 100){
            xVelocity = 100 *Math.signum(xVelocity);
        }


        yVelocity -= GRAVITY;
        xVelocity -= FRICTION *Math.signum(xVelocity);

            System.out.println(xVelocity);
        if(Math.abs(xVelocity)<(speed-FRICTION)){
            xVelocity =0;
        }
        moveWithCollision(platforms);




    }

    private void moveWithCollision(ArrayList<CollidableObject> collidableObjects){
        hitbox.x += xVelocity;// * Gdx.graphics.getDeltaTime();
        hitbox.y += yVelocity;// * Gdx.graphics.getDeltaTime();
        for (CollidableObject object:collidableObjects ) {
            while(hitbox.intersects(object.hitbox)){
                if(object instanceof Platform) {
                    hitbox.y++;
                    canJump = true;
                    yVelocity = 0;
                }
                else if( object instanceof Enemy){
                    Enemy enemy = (Enemy) object;
                    enemy.takeDamage();
                    
                    ((Enemy) object).takeDamage();
                }
            }
        }

    }


    public void draw(SpriteBatch batch){

        batch.draw(img,hitbox.x,hitbox.y,hitbox.width,hitbox.height);
    }

    public void dispose(){
        img.dispose();
    }






}
