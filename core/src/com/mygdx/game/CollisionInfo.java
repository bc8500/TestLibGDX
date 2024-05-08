package com.mygdx.game;

public class CollisionInfo {
    enum Side {
        TOP,
        LEFT,
        RIGHT,
        BOTTOM,
        NONE
    }
    float distance;
    Side side;
    CollidableObject object =null;

    public CollisionInfo(){
        this.distance = Float.MAX_VALUE;
        side = Side.NONE;
    }
    public CollisionInfo(float distance, Side side, CollidableObject object) {
        this.distance = distance;
        this.side = side;
        this.object =object;
    }
}
