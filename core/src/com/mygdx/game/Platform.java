package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class Platform  extends CollidableObject{

    public Platform(String path, int x, int y, int width, int height) {
        super(path, x, y, width, height);
    }
}
