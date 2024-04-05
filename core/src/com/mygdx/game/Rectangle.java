package com.mygdx.game;

import com.badlogic.gdx.math.Vector2;

public class Rectangle extends com.badlogic.gdx.math.Rectangle {
    public Rectangle(float x, float y, float width, float height) {
        super(x, y, width, height);
    }

    public Vector2 getBotRightPoint(){
        return new Vector2(this.x+this.width,this.y);
    }

    public Vector2 getTopRightPoint(){
        return new Vector2(this.x+this.width,this.y+this.height);
    }
    public Vector2 getBotLeftPoint(){
       return new Vector2(this.x,this.y);
    }
    public Vector2 getTopLeftPoint(){
        return new  Vector2(this.x,this.y+this.height);
    }
}
